package org.mc536.webservice.domain.model.dao;

import org.mc536.webservice.domain.model.entity.Company;

import java.util.List;

public interface CompanyDAO {

    Company create(Company company);

    void update(Company company);

    List<Company> findAll();

    Company findById(Integer id);

    Company findByName(String name);

    void delete(Integer id);

    List<Company> search(String name,
                         Float overallRatingWeight,
                         Float cultureAndValuesRatingWeight,
                         Float seniorLeadershipRatingWeight,
                         Float compensationAndBenefitsRatingWeight,
                         Float careerOpportunitiesRatingWeight,
                         Float workLifeBalanceRatingWeight,
                         Float recomendToFriendWeight,
                         Integer limit);
}
