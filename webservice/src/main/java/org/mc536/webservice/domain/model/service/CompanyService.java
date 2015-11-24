package org.mc536.webservice.domain.model.service;

import org.apache.commons.lang3.Validate;
import org.mc536.webservice.domain.model.dao.CompanyDAO;
import org.mc536.webservice.domain.model.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private static final int RECOMMENDATIONS_LIMIT = 10;

    @Autowired
    private CompanyDAO companyDAO;

    public Company createCompany(String name,
                                 String website,
                                 String industry,
                                 String logo) {

        Company company = new Company();
        company.setName(normalize(name));
        company.setWebsite(normalize(website));
        company.setIndustry(normalize(industry));
        company.setLogo(normalize(logo));
        company.setNumberOfRatings(0);
        company.setOverallRating(0.0F);
        company.setCultureAndValuesRating(0.0F);
        company.setSeniorLeadershipRating(0.0F);
        company.setCompensationAndBenefitsRating(0.0F);
        company.setCareerOpportunitiesRating(0.0F);
        company.setWorkLifeBalanceRating(0.0F);
        company.setRecomendToFriend(0.0F);
        company.setCeoAproval(0);

        try {
            companyDAO.create(company);
            return company;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Cannot create company with given parameters", e);
        }
    }

    public Company updateCompany(Integer id,
                                 String name,
                                 String website,
                                 String industry,
                                 String logo) {

        Company company = companyDAO.findById(id);
        Validate.notNull(company, "Cannot find company with Id=" + id);

        company.setName(normalize(name));
        company.setWebsite(normalize(website));
        company.setIndustry(normalize(industry));
        company.setLogo(normalize(logo));

        try {
            companyDAO.update(company);
            return company;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Cannot update company #" + id + " with given parameters", e);
        }
    }

    public void delete(Integer id) {
        companyDAO.delete(id);
    }

    public List<Company> findAll() {
        return companyDAO.findAll();
    }

    public Company findById(Integer id) {
        return companyDAO.findById(id);
    }

    public Company findByName(String name) {
        return companyDAO.findByName(name);
    }

    public List<Company> search(String name,
                                Boolean overallRatingWeigth,
                                Boolean cultureAndValuesRatingWeight,
                                Boolean seniorLeadershipRatingWeight,
                                Boolean compensationAndBenefitsRatingWeight,
                                Boolean careerOpportunitiesRatingWeight,
                                Boolean workLifeBalanceRatingWeight,
                                Boolean recomendToFriendWeight,
                                Integer limit) {

        return companyDAO.search(name,
                Boolean.TRUE.equals(overallRatingWeigth) ? 1.0F : 0.0F,
                Boolean.TRUE.equals(cultureAndValuesRatingWeight) ? 1.0F : 0.0F,
                Boolean.TRUE.equals(seniorLeadershipRatingWeight) ? 1.0F : 0.0F,
                Boolean.TRUE.equals(compensationAndBenefitsRatingWeight) ? 1.0F : 0.0F,
                Boolean.TRUE.equals(careerOpportunitiesRatingWeight) ? 1.0F : 0.0F,
                Boolean.TRUE.equals(workLifeBalanceRatingWeight) ? 1.0F : 0.0F,
                Boolean.TRUE.equals(recomendToFriendWeight) ? 1.0F : 0.0F,
                limit != null ? limit : RECOMMENDATIONS_LIMIT);
    }

    public boolean exists(Integer id) {
        return findById(id) != null;
    }

    public boolean exists(String name) {
        return findByName(name) != null;
    }

    private String normalize(String text) {
        Validate.notNull(text, "Text cannot be null");

        String normalized = text.trim();
        Validate.notBlank(normalized, "Text cannot be blank");

        return normalized;
    }
}
