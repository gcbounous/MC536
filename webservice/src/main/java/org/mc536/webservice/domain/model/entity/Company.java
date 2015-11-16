package org.mc536.webservice.domain.model.entity;

public class Company {

    private Integer id;
    private String name;
    private String website;
    private String industry;
    private Integer numberOfRatings;
    private String logo;
    private Double overallRating;
    private Double cultureAndValuesRating;
    private Double seniorLeadershipRating;
    private Double compensationAndBenefitsRating;
    private Double careerOpportunitiesRating;
    private Double workLifeBalanceRating;
    private Double recomendToFriend;
    private Integer CEOAproval;

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
        logo = logo;
    }

    public Double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Double overallRating) {
        this.overallRating = overallRating;
    }

    public Double getCultureAndValuesRating() {
        return cultureAndValuesRating;
    }

    public void setCultureAndValuesRating(Double cultureAndValuesRating) {
        this.cultureAndValuesRating = cultureAndValuesRating;
    }

    public Double getSeniorLeadershipRating() {
        return seniorLeadershipRating;
    }

    public void setSeniorLeadershipRating(Double seniorLeadershipRating) {
        this.seniorLeadershipRating = seniorLeadershipRating;
    }

    public Double getCompensationAndBenefitsRating() {
        return compensationAndBenefitsRating;
    }

    public void setCompensationAndBenefitsRating(Double compensationAndBenefitsRating) {
        this.compensationAndBenefitsRating = compensationAndBenefitsRating;
    }

    public Double getCareerOpportunitiesRating() {
        return careerOpportunitiesRating;
    }

    public void setCareerOpportunitiesRating(Double careerOpportunitiesRating) {
        this.careerOpportunitiesRating = careerOpportunitiesRating;
    }

    public Double getWorkLifeBalanceRating() {
        return workLifeBalanceRating;
    }

    public void setWorkLifeBalanceRating(Double workLifeBalanceRating) {
        this.workLifeBalanceRating = workLifeBalanceRating;
    }

    public Double getRecomendToFriend() {
        return recomendToFriend;
    }

    public void setRecomendToFriend(Double recomendToFriend) {
        this.recomendToFriend = recomendToFriend;
    }

    public Integer getCEOAproval() {
        return CEOAproval;
    }

    public void setCEOAproval(Integer CEOAproval) {
        this.CEOAproval = CEOAproval;
    }
}
