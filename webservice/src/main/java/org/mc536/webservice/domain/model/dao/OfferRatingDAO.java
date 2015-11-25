package org.mc536.webservice.domain.model.dao;

import org.mc536.webservice.domain.model.entity.OfferRating;

import java.util.List;

public interface OfferRatingDAO {

    List<OfferRating> findUserRatings(Integer userId);

    void rateOffer(OfferRating offerRating);

    void unrateOffer(Integer userId, Integer offerId);
}
