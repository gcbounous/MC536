package org.mc536.webservice.domain.model.dao;

import org.mc536.webservice.domain.model.entity.Offer;
import org.mc536.webservice.domain.model.entity.User;

import java.util.List;
import java.util.Set;

public interface OfferDAO {

    Offer create(Offer offer);

    void update(Offer offer);

    List<Offer> findAll();

    List<Offer> findAllExcept(Integer id);

    List<Offer> findAllExcept(Set<Integer> ids);

    Offer findById(Integer id);

    List<Offer> findById(Set<Integer> ids);

    Offer findByUrl(String url);

    List<Offer> findByCompanyId(Integer companyId);

    List<Offer> findBySkill(String skill);

    List<Offer> search(Set<String> skills,
                       Float overallRatingWeight,
                       Float cultureAndValuesRatingWeight,
                       Float seniorLeadershipRatingWeight,
                       Float compensationAndBenefitsRatingWeight,
                       Float careerOpportunitiesRatingWeight,
                       Float workLifeBalanceRatingWeight,
                       Float recomendToFriendWeight,
                       Integer limit);

    List<User> recommendedUsers(Integer offerId, Integer limit);

    void delete(Integer id);
}
