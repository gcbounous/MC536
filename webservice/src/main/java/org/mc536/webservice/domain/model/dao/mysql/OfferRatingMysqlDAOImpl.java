package org.mc536.webservice.domain.model.dao.mysql;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.mc536.webservice.domain.model.dao.OfferRatingDAO;
import org.mc536.webservice.domain.model.entity.OfferRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OfferRatingMysqlDAOImpl implements OfferRatingDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<OfferRating> findUserRatings(Integer userId) {
        return sessionFactory.getCurrentSession()
                .createCriteria(OfferRating.class)
                .add(Restrictions.eq("userId", userId))
                .list();
    }

    @Override
    public void rateOffer(OfferRating offerRating) {
        sessionFactory.getCurrentSession().saveOrUpdate(offerRating);
    }

    @Override
    public void unrateOffer(Integer userId, Integer offerId) {
        OfferRating offerRating = new OfferRating();
        offerRating.setUserId(userId);
        offerRating.setOfferId(offerId);
        sessionFactory.getCurrentSession().delete(offerRating);
    }
}
