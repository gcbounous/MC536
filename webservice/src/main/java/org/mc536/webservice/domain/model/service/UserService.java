package org.mc536.webservice.domain.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.mc536.webservice.domain.model.dao.UserDAO;
import org.mc536.webservice.domain.model.entity.User;
import org.mc536.webservice.domain.model.entity.Offer;

@Service
public class UserService {

    private static final int RECOMMENDATIONS_LIMIT = 10;

    @Autowired
    private UserDAO userDAO;

    public User createUser(String name) {
        User user = new User();
        user.setName(name);

        try {
            userDAO.create(user);
            return user;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public User updateUser(Integer id, String name) {
        User user = userDAO.findById(id);
        user.setName(name);

        try {
            userDAO.update(user);
            return user;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User findById(Integer id) {
        return userDAO.findById(id);
    }

    public boolean exists(Integer id) {
        return findById(id) != null;
    }

    public void delete(Integer id) {
        userDAO.delete(id);
    }

    public List<Offer> recommendedOffers(Integer id, Integer limit) {
        return userDAO.recommendedOffers(id, limit != null ? limit : RECOMMENDATIONS_LIMIT);
    }
}
