package org.mc536.webservice.domain.model.service;

import java.util.List;

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

    public List<OfferRating> offerRatings(Integer userId) {
        return offerRatingDAO.findUserRatings(userId);
    }
}
