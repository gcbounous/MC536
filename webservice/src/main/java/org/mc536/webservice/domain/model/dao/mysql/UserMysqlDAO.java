package org.mc536.webservice.domain.model.dao.mysql;

import java.util.List;
import javax.transaction.Transactional;
import org.apache.commons.lang3.Validate;
import org.hibernate.SessionFactory;
import org.mc536.webservice.domain.model.dao.UserDAO;
import org.mc536.webservice.domain.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserMysqlDAO implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User create(User user) {
	Validate.notNull(user, "Cannot create a null user");
        Validate.isTrue(user.getId() == null, "Cannot specify an Id for a new user");

        Integer id = (Integer) sessionFactory.getCurrentSession().save(user);
        user.setId(id);

        return user;
    }

    @Override
    public void update(User user) {
        Validate.notNull(user, "Cannot create a null user");
        Validate.notNull(user.getId(), "Cannot update a user with no Id");

        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public List<User> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(User.class).list();
    }

    @Override
    public User findById(Integer id) {
        Validate.notNull(id, "Cannot find user with null Id");

        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public void delete(Integer id) {
        Validate.notNull(id, "Cannot delete user with null Id");

        User user = new User();
        user.setId(id);
        sessionFactory.getCurrentSession().delete(user);
    }

}
