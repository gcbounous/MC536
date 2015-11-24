package org.mc536.webservice.domain.model.dao;

import org.mc536.webservice.domain.model.entity.User;
import org.mc536.webservice.domain.model.entity.Offer;
import java.util.List;

public interface UserDAO {

    User create(User user);

    void update(User user);

    List<User> findAll();

    User findById(Integer id);

    void delete(Integer id);

    List<Offer> recommendedOffers(Integer id, Integer limit);
}
