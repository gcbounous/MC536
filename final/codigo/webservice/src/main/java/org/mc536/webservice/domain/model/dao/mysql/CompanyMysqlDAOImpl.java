package org.mc536.webservice.domain.model.dao.mysql;

import org.apache.commons.lang3.Validate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.mc536.webservice.domain.model.dao.CompanyDAO;
import org.mc536.webservice.domain.model.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CompanyMysqlDAOImpl implements CompanyDAO {

    private static final String SEARCH_QUERY = "" +
            "SELECT c FROM Company AS c" +
            "    WHERE lower(c.name) LIKE :name OR :any_name IS TRUE" +
            "    ORDER BY (" +
            "        c.overallRating * :overallRatingWeigth + " +
            "        c.cultureAndValuesRating * :cultureAndValuesRatingWeight +" +
            "        c.seniorLeadershipRating * :seniorLeadershipRatingWeight +" +
            "        c.compensationAndBenefitsRating * :compensationAndBenefitsRatingWeight +" +
            "        c.careerOpportunitiesRating * :careerOpportunitiesRatingWeight +" +
            "        c.workLifeBalanceRating * :workLifeBalanceRatingWeight +" +
            "        c.recomendToFriend * :recomendToFriendWeight" +
            "    ) DESC," +
            "    c.numberOfRatings DESC";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Company create(final Company company) {
        Integer id = (Integer) sessionFactory.getCurrentSession().save(company);
        company.setId(id);

        return company;
    }

    @Override
    public void update(Company company) {
        sessionFactory.getCurrentSession().update(company);
    }

    @Override
    public List<Company> findAll() {
        return getCriteria().list();
    }

    @Override
    public Company findById(Integer id) {
        return (Company) sessionFactory.getCurrentSession().get(Company.class, id);
    }

    @Override
    public Company findByName(String name) {
        return (Company) getCriteria().add(Restrictions.eq("name", name)).uniqueResult();
    }

    @Override
    public List<Company> search(String name,
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
        query.setString("name", name != null ? "%" + name.toLowerCase() + "%" : "");
        query.setBoolean("any_name", name == null || "".equals(name));
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
        Validate.notNull(id, "Cannot delete company with null Id");

        Company company = new Company();
        company.setId(id);
        sessionFactory.getCurrentSession().delete(company);
    }

    private Criteria getCriteria() {
        return sessionFactory.getCurrentSession().createCriteria(Company.class);
    }
}
