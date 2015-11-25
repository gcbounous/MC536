package org.mc536.webservice.domain.model.service.recommendation;

import org.mc536.webservice.domain.model.dao.OfferDAO;
import org.mc536.webservice.domain.model.dao.OfferRatingDAO;
import org.mc536.webservice.domain.model.entity.Company;
import org.mc536.webservice.domain.model.entity.Offer;
import org.mc536.webservice.domain.model.entity.OfferRating;
import org.mc536.webservice.domain.model.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private OfferRatingDAO offerRatingDAO;

    @Autowired
    private OfferDAO offerDAO;

    @Value("${recommendation.limit}")
    private int defaultLimit;

    @Value("${recommendation.min_score}")
    private double minScore;

    @Value("${recommendation.weight.skills_cos}")
    private double skillsCosineWeight;

    @Value("${recommendation.weight.rankings_cos}")
    private double rankingsCosineWeight;

    @Value("${recommendation.weight.rankings_f}")
    private double rankingsFactorWeight;

    public List<Recommendation<Offer>> recommendOffers(Integer userId, Integer limit) {

        List<OfferRating> offerRatings = offerRatingDAO.findUserRatings(userId);
        if (offerRatings.isEmpty()) {
            return offerDAO.findAll(limit != null ? limit : defaultLimit)
                    .stream()
                    .map(o -> new Recommendation<>(o, 0.0))
                    .collect(Collectors.toList());
        }

        Set<Integer> offersIds = offerRatings.stream()
                .map(OfferRating::getOfferId)
                .collect(Collectors.toSet());

        Map<Integer, Integer> ratingsMap = offerRatings.stream()
                .collect(Collectors.toMap(OfferRating::getOfferId, OfferRating::getGrade));

        return findSimilarOffers(offersIds, ratingsMap, limit);
    }

    public List<Recommendation<Offer>> findSimilarOffers(Set<Integer> ids, Integer limit) {
        return findSimilarOffers(ids, new HashMap<>(), limit);
    }

    public List<Recommendation<Offer>> findSimilarOffers(Set<Integer> ids, Map<Integer, Integer> ratingsMap, Integer limit) {
        List<Offer> baseOffers = offerDAO.findById(ids);
        List<Offer> offers = offerDAO.findAllExcept(ids);
        return findSimilarOffers(baseOffers, ratingsMap, offers, limit);
    }

    public List<Recommendation<Offer>> findSimilarOffers(Integer id, Integer limit) {
        return findSimilarOffers(id, new HashMap<>(), limit);
    }

    public List<Recommendation<Offer>> findSimilarOffers(Integer id, Map<Integer, Integer> ratingsMap, Integer limit) {
        List<Offer> baseOffers = Arrays.asList(offerDAO.findById(id));
        List<Offer> offers = offerDAO.findAllExcept(id);
        return findSimilarOffers(baseOffers, ratingsMap, offers, limit);
    }

    private List<Recommendation<Offer>> findSimilarOffers(List<Offer> baseOffers, Map<Integer, Integer> ratingsMap, List<Offer> offers, Integer limit) {
        return offers
                .stream()
                .map(o -> new Recommendation<>(o, similarity(baseOffers, ratingsMap, o)))
                .filter(os -> os.getScore() >= minScore)
                .sorted((o1, o2) -> Double.compare(o2.getScore(), o1.getScore()))
                .limit(limit != null ? limit : defaultLimit)
                .collect(Collectors.toList());
    }

    private double similarity(List<Offer> baseOffers, Map<Integer, Integer> ratingsMap, Offer offer) {
        double t = baseOffers
                .stream()
                .map(bo -> similarity(bo, offer) * rating(bo, ratingsMap))
                .reduce(0.0, (k, s) -> k + s);

        return t / baseOffers.size();
    }

    private double rating(Offer offer, Map<Integer, Integer> ratingsMap) {
        Integer r = ratingsMap.get(offer.getId());
        return r != null ? r : 0;
    }

    private double similarity(Offer o1, Offer o2) {
        double scos = similarity(o1.getSkills(), o2.getSkills());
        double rcos = similarity(o1.getCompany(), o2.getCompany());
        double rf = rankingsFactor(o1.getCompany(), o2.getCompany());

        double s = scos * skillsCosineWeight + rcos * rankingsCosineWeight + rf * rankingsFactorWeight;
        double d = skillsCosineWeight + rankingsCosineWeight + rankingsFactorWeight;
        return s / d;
    }

    private double similarity(Set<Skill> s1, Set<Skill> s2) {
        String[] skillsSet = skillsSet(s1, s2);
        double[] c1 = componentsArray(s1, skillsSet);
        double[] c2 = componentsArray(s2, skillsSet);

        return cosineSimilarity(c1, c2);
    }

    private double similarity(Company c1, Company c2) {
        double[] r1 = rankingsArray(c1);
        double[] r2 = rankingsArray(c2);

        double s = cosineSimilarity(r1, r2);
        return !Double.isNaN(s) ? s : 0;
    }

    private double cosineSimilarity(double[] a, double[] b) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < a.length; i++) {
            dotProduct += a[i] * b[i];
            normA += Math.pow(a[i], 2);
            normB += Math.pow(b[i], 2);
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private String[] skillsSet(Set<Skill> s1, Set<Skill> s2) {
        Set<String> skillsSet = s1
                .stream()
                .map(Skill::getName)
                .collect(Collectors.toSet());

        s2.stream()
                .filter(s -> !skillsSet.contains(s.getName()))
                .forEach(s -> skillsSet.add(s.getName()));

        String[] array = new String[skillsSet.size()];
        return skillsSet.toArray(array);
    }

    private double[] componentsArray(Set<Skill> skills, String[] skillsSet) {
        Set<String> snames = skills.stream().map(Skill::getName).collect(Collectors.toSet());
        return componentsArray(snames, skillsSet);
    }

    private double[] componentsArray(Collection<String> strings, String[] stringSet) {
        double[] array = new double[stringSet.length];

        for (int i = 0; i < stringSet.length; i++) {
            array[i] = strings.contains(stringSet[i]) ? 1 : 0;
        }

        return array;
    }

    private double[] rankingsArray(Company c) {
        return new double[]{
                c.getOverallRating(),
                c.getCultureAndValuesRating(),
                c.getSeniorLeadershipRating(),
                c.getCompensationAndBenefitsRating(),
                c.getCareerOpportunitiesRating(),
                c.getWorkLifeBalanceRating(),
                c.getRecomendToFriend()
        };
    }

    private double rankingsFactor(Company c1, Company c2) {
        double r1 = generalRanking(c1);
        double r2 = generalRanking(c2);

        if (r1 == 0 || r2 == 0) {
            return r1 == r2 ? 1 : 0;
        }

        return Math.min(r1, r2) / Math.max(r1, r2);
    }

    private double generalRanking(Company c) {
        double f = Math.log(c.getNumberOfRatings()) / Math.log(10);

        double r = c.getOverallRating()
                * c.getCultureAndValuesRating()
                * c.getSeniorLeadershipRating()
                * c.getCompensationAndBenefitsRating()
                * c.getCareerOpportunitiesRating()
                * c.getWorkLifeBalanceRating()
                * c.getRecomendToFriend();

        return Math.pow(r, f / 7);
    }
}
