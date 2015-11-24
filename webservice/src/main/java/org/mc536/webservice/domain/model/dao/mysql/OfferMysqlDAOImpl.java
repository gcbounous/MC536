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
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class OfferMysqlDAOImpl implements OfferDAO {

    private static final String FIND_BY_SKILL_QUERY = "" +
            "select o from " + Offer.class.getName() + " as o" +
            " inner join o.skills as s" +
            " with s.name = :skill";

    private static final String SEARCH_QUERY = "" +
            "select o from " + Offer.class.getName() + " as o" +
            " inner join o.skills as s" +
            " with s.name in (:skills) or :any_skill is true" +
            " inner join o.company as c" +
            " order by (" +
            " c.overallRating * :overallRatingWeigth + " +
            " c.cultureAndValuesRating * :cultureAndValuesRatingWeight +" +
            " c.seniorLeadershipRating * :seniorLeadershipRatingWeight +" +
            " c.compensationAndBenefitsRating * :compensationAndBenefitsRatingWeight +" +
            " c.careerOpportunitiesRating * :careerOpportunitiesRatingWeight +" +
            " c.workLifeBalanceRating * :workLifeBalanceRatingWeight +" +
            " c.recomendToFriend * :recomendToFriendWeight" +
            " ) desc," +
            " o.publicationDate desc";

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
    public List<Offer> search(Set<String> skills,
                              Float overallRatingWeigth,
                              Float cultureAndValuesRatingWeight,
                              Float seniorLeadershipRatingWeight,
                              Float compensationAndBenefitsRatingWeight,
                              Float careerOpportunitiesRatingWeight,
                              Float workLifeBalanceRatingWeight,
                              Float recomendToFriendWeight,
                              Integer limit) {

        Query query = sessionFactory.getCurrentSession().createQuery(SEARCH_QUERY);

        query.setMaxResults(limit);
        query.setParameterList("skills", !CollectionUtils.isEmpty(skills) ? skills : new HashSet<>(Arrays.asList(" ")));
        query.setBoolean("any_skill", CollectionUtils.isEmpty(skills));
        query.setFloat("overallRatingWeigth", overallRatingWeigth);
        query.setFloat("cultureAndValuesRatingWeight", cultureAndValuesRatingWeight);
        query.setFloat("seniorLeadershipRatingWeight", seniorLeadershipRatingWeight);
        query.setFloat("compensationAndBenefitsRatingWeight", compensationAndBenefitsRatingWeight);
        query.setFloat("careerOpportunitiesRatingWeight", careerOpportunitiesRatingWeight);
        query.setFloat("workLifeBalanceRatingWeight", workLifeBalanceRatingWeight);
        query.setFloat("recomendToFriendWeight", recomendToFriendWeight);
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
        return sessionFactory.getCurrentSession()
                .createCriteria(Offer.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }
}
