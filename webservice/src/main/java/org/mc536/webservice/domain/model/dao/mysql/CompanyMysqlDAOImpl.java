package org.mc536.webservice.domain.model.dao.mysql;

import org.apache.commons.lang3.Validate;
import org.hibernate.Criteria;
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
