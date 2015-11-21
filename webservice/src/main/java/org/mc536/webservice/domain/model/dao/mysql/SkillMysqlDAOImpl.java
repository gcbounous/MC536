package org.mc536.webservice.domain.model.dao.mysql;

import org.apache.commons.lang3.Validate;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.mc536.webservice.domain.model.dao.SkillDAO;
import org.mc536.webservice.domain.model.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class SkillMysqlDAOImpl implements SkillDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Skill create(Skill skill) {
        Validate.notNull(skill, "Cannot create a null skill");
        Validate.isTrue(skill.getId() == null, "Cannot specify an Id for a new skill");

        Integer id = (Integer) sessionFactory.getCurrentSession().save(skill);
        skill.setId(id);

        return skill;
    }

    @Override
    public void update(Skill skill) {
        Validate.notNull(skill, "Cannot create a null skill");
        Validate.notNull(skill.getId(), "Cannot update a skill with no Id");

        sessionFactory.getCurrentSession().update(skill);
    }

    @Override
    public List<Skill> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Skill.class).list();
    }

    @Override
    public Skill findById(Integer id) {
        Validate.notNull(id, "Cannot find skill with null Id");

        return (Skill) sessionFactory.getCurrentSession().get(Skill.class, id);
    }

    @Override
    public Skill findByName(String name) {
        Validate.notBlank(name, "Cannot find skill with blank name");

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Skill.class);

        return (Skill) criteria.add(Restrictions.eq("name", name)).uniqueResult();
    }

    @Override
    public void delete(Integer id) {
        Validate.notNull(id, "Cannot delete skill with null Id");

        Skill skill = new Skill();
        skill.setId(id);
        sessionFactory.getCurrentSession().delete(skill);
    }
}