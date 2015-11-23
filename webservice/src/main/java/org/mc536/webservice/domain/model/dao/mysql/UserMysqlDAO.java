package org.mc536.webservice.domain.model.dao.mysql;

import java.util.List;
import javax.transaction.Transactional;
import org.apache.commons.lang3.Validate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.mc536.webservice.domain.model.dao.UserDAO;
import org.mc536.webservice.domain.model.entity.User;
import org.mc536.webservice.domain.model.entity.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserMysqlDAO implements UserDAO {

    private static final String RECOMMENDATION_QUERY = "" +
            "SELECT DISTINCT O FROM Offer O, Company C, Demands D, User_Skill US, User U" +
            " WHERE O.companyId = C.id AND O.id = D.OfferId AND D.SkillId = US.SkillId AND US.UserId = U.id AND U.id = :id AND C.numberOfRatings > 50" +
            " ORDER BY C.overallRating DESC, O.publicationDate DESC";

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

    @Override
    public List<Offer> recommendations(Integer id, Integer limit) {
        Query query = sessionFactory.getCurrentSession().createQuery(RECOMMENDATION_QUERY);
        query.setInteger("id", id);
        query.setMaxResults(limit);
        return query.list();
    }
}