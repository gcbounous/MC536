package org.mc536.webservice.domain.model.dao.mysql;

import org.apache.commons.lang3.Validate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.mc536.webservice.domain.model.dao.OfferDAO;
import org.mc536.webservice.domain.model.entity.Offer;
import org.mc536.webservice.domain.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.*;

import static org.hibernate.criterion.Restrictions.*;

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
            " c.overallRating * :overallRatingWeight + " +
            " c.cultureAndValuesRating * :cultureAndValuesRatingWeight +" +
            " c.seniorLeadershipRating * :seniorLeadershipRatingWeight +" +
            " c.compensationAndBenefitsRating * :compensationAndBenefitsRatingWeight +" +
            " c.careerOpportunitiesRating * :careerOpportunitiesRatingWeight +" +
            " c.workLifeBalanceRating * :workLifeBalanceRatingWeight +" +
            " c.recomendToFriend * :recomendToFriendWeight" +
            " ) desc," +
            " o.publicationDate desc";

    private static final String RECOMMENDED_USERS_QUERY = "" +
            "SELECT u FROM Offer AS o, User AS u" +
            "    INNER JOIN o.skills AS os" +
            "    INNER JOIN u.skills AS us" +
            "    WHERE o.id = :id AND os.id = us.id" +
            "    ORDER BY count(us) DESC";

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
    public List<Offer> findAll(Integer limit) {
        return getCriteria().setMaxResults(limit).list();
    }

    @Override
    public List<Offer> findAllExcept(Integer id) {
        return getCriteria().add(ne("id", id)).list();
    }

    @Override
    public List<Offer> findAllExcept(Set<Integer> ids) {
        Criteria criteria = getCriteria();
        if (ids != null && !ids.isEmpty()) {
            criteria.add(not(in("id", ids)));
        }
        return criteria.list();
    }

    @Override
    public Offer findById(Integer id) {
        return (Offer) sessionFactory.getCurrentSession().get(Offer.class, id);
    }

    @Override
    public List<Offer> findById(Set<Integer> ids) {
        return ids != null && !ids.isEmpty() ? getCriteria().add(in("id", ids)).list() : new ArrayList<>();
    }

    @Override
    public Offer findByUrl(String url) {
        return (Offer) getCriteria().add(eq("url", url)).uniqueResult();
    }

    @Override
    public List<Offer> findByCompanyId(Integer companyId) {
        return getCriteria().add(eq("companyId", companyId)).list();
    }

    @Override
    public List<Offer> findBySkill(String skill) {
        Query query = sessionFactory.getCurrentSession().createQuery(FIND_BY_SKILL_QUERY);
        query.setParameter("skill", skill);
        return query.list();
    }

    @Override
    public List<Offer> search(Set<String> skills,
                              Float overallRatingWeight,
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
        query.setFloat("overallRatingWeight", overallRatingWeight);
        query.setFloat("cultureAndValuesRatingWeight", cultureAndValuesRatingWeight);
        query.setFloat("seniorLeadershipRatingWeight", seniorLeadershipRatingWeight);
        query.setFloat("compensationAndBenefitsRatingWeight", compensationAndBenefitsRatingWeight);
        query.setFloat("careerOpportunitiesRatingWeight", careerOpportunitiesRatingWeight);
        query.setFloat("workLifeBalanceRatingWeight", workLifeBalanceRatingWeight);
        query.setFloat("recomendToFriendWeight", recomendToFriendWeight);
        return query.list();
    }

    @Override
    public List<User> recommendedUsers(Integer offerId, Integer limit){
        Query query = sessionFactory.getCurrentSession().createQuery(RECOMMENDED_USERS_QUERY);
        query.setInteger("id", offerId);
        query.setMaxResults(limit);
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
