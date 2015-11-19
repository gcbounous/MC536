package org.mc536.webservice.domain.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "Company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "CName", length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = "Website", length = 100)
    private String website;

    @Column(name = "Industry", length = 50)
    private String industry;

    @Column(name = "NumberOfRatings", nullable = false)
    private Integer numberOfRatings;

    @Column(name = "Logo", length = 150)
    private String logo;

    @Column(name = "OverallRating")
    private Float overallRating;

    @Column(name = "CultureAndValuesRating")
    private Float cultureAndValuesRating;

    @Column(name = "SeniorLeadershipRating")
    private Float seniorLeadershipRating;

    @Column(name = "CompensationAndBenefitsRating")
    private Float compensationAndBenefitsRating;

    @Column(name = "CareerOpportunitiesRating")
    private Float careerOpportunitiesRating;

    @Column(name = "WorkLifeBalanceRating")
    private Float workLifeBalanceRating;

    @Column(name = "RecomendToFriend")
    private Float recomendToFriend;

    @Column(name = "CEOAproval")
    private Integer ceoAproval;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Float getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Float overallRating) {
        this.overallRating = overallRating;
    }

    public Float getCultureAndValuesRating() {
        return cultureAndValuesRating;
    }

    public void setCultureAndValuesRating(Float cultureAndValuesRating) {
        this.cultureAndValuesRating = cultureAndValuesRating;
    }

    public Float getSeniorLeadershipRating() {
        return seniorLeadershipRating;
    }

    public void setSeniorLeadershipRating(Float seniorLeadershipRating) {
        this.seniorLeadershipRating = seniorLeadershipRating;
    }

    public Float getCompensationAndBenefitsRating() {
        return compensationAndBenefitsRating;
    }

    public void setCompensationAndBenefitsRating(Float compensationAndBenefitsRating) {
        this.compensationAndBenefitsRating = compensationAndBenefitsRating;
    }

    public Float getCareerOpportunitiesRating() {
        return careerOpportunitiesRating;
    }

    public void setCareerOpportunitiesRating(Float careerOpportunitiesRating) {
        this.careerOpportunitiesRating = careerOpportunitiesRating;
    }

    public Float getWorkLifeBalanceRating() {
        return workLifeBalanceRating;
    }

    public void setWorkLifeBalanceRating(Float workLifeBalanceRating) {
        this.workLifeBalanceRating = workLifeBalanceRating;
    }

    public Float getRecomendToFriend() {
        return recomendToFriend;
    }

    public void setRecomendToFriend(Float recomendToFriend) {
        this.recomendToFriend = recomendToFriend;
    }

    public Integer getCeoAproval() {
        return ceoAproval;
    }

    public void setCeoAproval(Integer ceoAproval) {
        this.ceoAproval = ceoAproval;
    }
}
