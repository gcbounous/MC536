package org.mc536.webservice.domain.model.service.recommendation;

import org.mc536.webservice.domain.model.dao.OfferDAO;
import org.mc536.webservice.domain.model.dao.OfferRatingDAO;
import org.mc536.webservice.domain.model.entity.Offer;
import org.mc536.webservice.domain.model.entity.OfferRating;
import org.mc536.webservice.domain.model.entity.Skill;
import org.mc536.webservice.domain.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceV2 {

    @Autowired
    private OfferDAO offerDAO;

    @Autowired
    private OfferRatingDAO offerRatingDAO;

    @Autowired
    private UserService userService;

    @Value("${recommendation.limit}")
    private int defaultLimit;

    @Value("${recommendation.v2.min_score}")
    private double minScore;

    public RecommendationResults<Offer> recommendOffers(Integer userId, Integer limit) {
        long start = System.currentTimeMillis();

        List<OfferRating> offerRatings = offerRatingDAO.findUserRatings(userId);
        if (offerRatings.isEmpty()) {
            List<Recommendation<Offer>> results = offerDAO.findAll(limit != null ? limit : defaultLimit)
                    .stream()
                    .map(o -> new Recommendation<>(o, 0.0))
                    .collect(Collectors.toList());

            return new RecommendationResults<>(System.currentTimeMillis() - start, results);
        }

        Map<String, Double> grades = userService.userProfile(offerRatings);

        Set<Integer> offersIds = offersIds(offerRatings);

        double minGrade = minGrade(grades);

        double maxGrade = maxGrade(grades);

        List<Recommendation<Offer>> results = offerDAO.findAllExcept(offersIds).stream()
                .map(o -> new Recommendation<>(o, similarity(grades, minGrade, maxGrade, o)))
                .filter(os -> os.getScore() >= minScore)
                .sorted((o1, o2) -> Double.compare(o2.getScore(), o1.getScore()))
                .limit(limit != null ? limit : defaultLimit)
                .collect(Collectors.toList());

        return new RecommendationResults<>(System.currentTimeMillis() - start, results);
    }

    private Set<Integer> offersIds(List<OfferRating> offerRatings) {
        return offerRatings.stream()
                .map(OfferRating::getOfferId)
                .collect(Collectors.toSet());
    }

    private double minGrade(Map<String, Double> grades) {
        return grades.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.summingDouble(g -> Math.min(g, 0)));
    }

    private double maxGrade(Map<String, Double> grades) {
        return grades.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.summingDouble(g -> Math.max(g, 0)));
    }

    private double similarity(Map<String, Double> grades, double minGrade, double maxGrade, Offer offer) {

        Set<Skill> skills = offer.getSkills();
        double k = skills.stream()
                .collect(Collectors.summingDouble(s -> grades.containsKey(s.getName()) ? 1.0 / skills.size() : 0));

        double g = offer.getSkills()
                .stream()
                .collect(Collectors.summingDouble(s -> get(grades, s.getName())));

        return k * (g - minGrade) / (maxGrade - minGrade);
    }

    private Double get(Map<String, Double> grades, String skill) {
        Double grade = grades.get(skill);
        return grade != null ? grade : 0;
    }
}
