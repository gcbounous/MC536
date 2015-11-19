package org.mc536.webservice.domain.model.dao.mysql;

import org.apache.commons.lang3.Validate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.mc536.webservice.domain.model.dao.OfferDAO;
import org.mc536.webservice.domain.model.entity.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OfferMysqlDAO implements OfferDAO {

    private static final String FIND_BY_SKILL_QUERY = "" +
            "select o from " + Offer.class.getName() + " as o" +
            " inner join o.skills as s" +
            " with s.name = :skill";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Offer create(Offer offer) {
        Integer id = (Integer) sessionFactory.getCurrentSession().save(offer);
        offer.setId(id);

        return offer;
    }

    @Override
    public void update(Offer offer) {
        sessionFactory.getCurrentSession().update(offer);
    }

    @Override
    public List<Offer> findAll() {
        return getCriteria().list();
    }

    @Override
    public Offer findById(Integer id) {
        return (Offer) sessionFactory.getCurrentSession().get(Offer.class, id);
    }

    @Override
    public Offer findByUrl(String url) {
        return (Offer) getCriteria().add(Restrictions.eq("url", url)).uniqueResult();
    }

    @Override
    public List<Offer> findByCompanyId(Integer companyId) {
        return getCriteria().add(Restrictions.eq("companyId", companyId)).list();
    }

    @Override
    public List<Offer> findBySkill(String skill) {
        Query query = sessionFactory.getCurrentSession().createQuery(FIND_BY_SKILL_QUERY);
        query.setParameter("skill", skill);
        return query.list();
    }

    @Override
    public void delete(Integer id) {
        Validate.notNull(id, "Cannot delete offer with null Id");

        Offer offer = new Offer();
        offer.setId(id);
        sessionFactory.getCurrentSession().delete(offer);
    }

    private Criteria getCriteria() {
        return sessionFactory.getCurrentSession().createCriteria(Offer.class);
    }
}
