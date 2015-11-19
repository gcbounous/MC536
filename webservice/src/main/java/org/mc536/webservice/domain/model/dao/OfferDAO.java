package org.mc536.webservice.domain.model.dao;

import org.mc536.webservice.domain.model.entity.Offer;

import java.util.List;

public interface OfferDAO {

    Offer create(Offer offer);

    void update(Offer offer);

    List<Offer> findAll();

    Offer findById(Integer id);

    Offer findByUrl(String url);

    List<Offer> findByCompanyId(Integer companyId);

    List<Offer> findBySkill(String skill);

    void delete(Integer id);
}
