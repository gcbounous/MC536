package org.mc536.webservice.domain.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mc536.webservice.domain.model.dao.OfferDAO;
import org.mc536.webservice.domain.model.dao.OfferRatingDAO;
import org.mc536.webservice.domain.model.entity.OfferRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.mc536.webservice.domain.model.dao.UserDAO;
import org.mc536.webservice.domain.model.entity.User;
import org.mc536.webservice.domain.model.entity.Offer;

@Service
public class UserService {

    private static final int LIMIT = 10;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OfferDAO offerDAO;

    @Autowired
    private OfferRatingDAO offerRatingDAO;

    @Value("${recommendation.grade.like}")
    private int likeGrade;

    @Value("${recommendation.grade.dislike}")
    private int dislikeGrade;

    public User createUser(String name) {
        User user = new User();
        user.setName(name);

        try {
            userDAO.create(user);
            return user;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public User updateUser(Integer id, String name) {
        User user = userDAO.findById(id);
        user.setName(name);

        try {
            userDAO.update(user);
            return user;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User findById(Integer id) {
        return userDAO.findById(id);
    }

    public boolean exists(Integer id) {
        return findById(id) != null;
    }

    public void delete(Integer id) {
        userDAO.delete(id);
    }

    public List<Offer> recommendedOffers(Integer id, Integer limit) {
        return userDAO.recommendedOffers(id, limit != null ? limit : LIMIT);
    }

    public OfferRating likeOffer(Integer userId, Integer offerId) {
        OfferRating offerRating = new OfferRating();
        offerRating.setUserId(userId);
        offerRating.setOfferId(offerId);
        offerRating.setGrade(likeGrade);

        offerRatingDAO.rateOffer(offerRating);
        return offerRating;
    }

    public OfferRating dislikeOffer(Integer userId, Integer offerId) {
        OfferRating offerRating = new OfferRating();
        offerRating.setUserId(userId);
        offerRating.setOfferId(offerId);
        offerRating.setGrade(dislikeGrade);

        offerRatingDAO.rateOffer(offerRating);
        return offerRating;
    }

    public void unrateOffer(Integer userId, Integer offerId) {
        offerRatingDAO.unrateOffer(userId, offerId);
    }

    public void unrateAllOffers(Integer userId) {
        offerRatingDAO.clearUserRatings(userId);
    }

    public List<OfferRating> offerRatings(Integer userId) {
        return offerRatingDAO.findUserRatings(userId);
    }

    public Map<String, Double> userProfile(Integer userId) {
        return userProfile(offerRatingDAO.findUserRatings(userId));
    }

    public Map<String, Double> userProfile(List<OfferRating> offerRatings) {
        Map<String, Double> grades = new HashMap<>();

        offerRatings.stream()
                .forEach(r -> {
                    offerDAO.findById(r.getOfferId())
                            .getSkills()
                            .stream()
                            .forEach(s -> incGrade(grades, s.getName(), r.getGrade()));
                });

        return grades;
    }

    private void incGrade(Map<String, Double> grades, String skill, Integer value) {
        Double grade = grades.get(skill);
        grades.put(skill, value.doubleValue() + (grade != null ? grade : 0));
    }
}
