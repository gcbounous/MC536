package org.mc536.webservice.domain.model.service;

import org.apache.commons.lang3.Validate;
import org.mc536.webservice.domain.model.dao.OfferDAO;
import org.mc536.webservice.domain.model.entity.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class OfferService {

    private static final int SEARCH_LIMIT = 10;

    @Autowired
    private OfferDAO offerDAO;

    @Autowired
    private CompanyService companyService;

    public Offer postOffer(String title,
                           String description,
                           String location,
                           String url,
                           Integer companyId) {

        Validate.isTrue(companyService.exists(companyId), "Company with Id=" + companyId + " does not exist");

        Offer offer = new Offer();
        offer.setTitle(normalize(title));
        offer.setDescription(normalize(description));
        offer.setLocation(normalize(location));
        offer.setUrl(normalize(url));
        offer.setPublicationDate(new Date());
        offer.setUpdated(new Date());
        offer.setCompanyId(companyId);

        try {
            offerDAO.create(offer);
            return offer;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Cannot create offer with given parameters", e);
        }
    }

    public Offer updateOffer(Integer id,
                             String title,
                             String description,
                             String location,
                             String url) {

        Offer offer = offerDAO.findById(id);
        Validate.notNull(offer, "Cannot find offer with Id=" + id);

        offer.setTitle(normalize(title));
        offer.setDescription(normalize(description));
        offer.setLocation(normalize(location));
        offer.setUrl(normalize(url));
        offer.setUpdated(new Date());

        try {
            offerDAO.update(offer);
            return offer;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Cannot update offer #" + id + " with given parameters", e);
        }
    }

    public List<Offer> findAll() {
        return offerDAO.findAll();
    }

    public Offer findById(Integer id) {
        return offerDAO.findById(id);
    }

    public List<Offer> findByCompanyid(Integer companyId) {
        return offerDAO.findByCompanyId(companyId);
    }

    public List<Offer> findBySkill(String skill) {
        return offerDAO.findBySkill(skill);
    }

    public List<Offer> search(Set<String> skills,
                              Boolean overallRatingWeigth,
                              Boolean cultureAndValuesRatingWeight,
                              Boolean seniorLeadershipRatingWeight,
                              Boolean compensationAndBenefitsRatingWeight,
                              Boolean careerOpportunitiesRatingWeight,
                              Boolean workLifeBalanceRatingWeight,
                              Boolean recomendToFriendWeight,
                              Integer limit) {

        return offerDAO.search(skills,
                Boolean.TRUE.equals(overallRatingWeigth) ? 1.0F : 0.0F,
                Boolean.TRUE.equals(cultureAndValuesRatingWeight) ? 1.0F : 0.0F,
                Boolean.TRUE.equals(seniorLeadershipRatingWeight) ? 1.0F : 0.0F,
                Boolean.TRUE.equals(compensationAndBenefitsRatingWeight) ? 1.0F : 0.0F,
                Boolean.TRUE.equals(careerOpportunitiesRatingWeight) ? 1.0F : 0.0F,
                Boolean.TRUE.equals(workLifeBalanceRatingWeight) ? 1.0F : 0.0F,
                Boolean.TRUE.equals(recomendToFriendWeight) ? 1.0F : 0.0F,
                limit != null ? limit : SEARCH_LIMIT);
    }

    public boolean exists(Integer id) {
        return findById(id) != null;
    }

    public void delete(Integer id) {
        offerDAO.delete(id);
    }

    private String normalize(String text) {
        Validate.notNull(text, "Text cannot be null");

        String normalized = text.trim();
        Validate.notBlank(normalized, "Text cannot be blank");

        return normalized;
    }
}
