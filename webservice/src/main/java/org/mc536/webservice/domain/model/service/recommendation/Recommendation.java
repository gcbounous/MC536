package org.mc536.webservice.domain.model.service.recommendation;

import org.mc536.webservice.domain.model.entity.Offer;

public class Recommendation<T> {

    private T entity;

    private Double score;

    public Recommendation() {}

    public Recommendation(T entity, Double score) {
        this.entity = entity;
        this.score = score;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
