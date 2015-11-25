package org.mc536.webservice.domain.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Offer_Rating")
public class OfferRating implements Serializable {

    @Id
    @Column(name = "UserId", nullable = false)
    private Integer userId;

    @Id
    @Column(name = "OfferId", nullable = false)
    private Integer offerId;

    @Column(name = "Grade", nullable = false)
    private Integer grade;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
