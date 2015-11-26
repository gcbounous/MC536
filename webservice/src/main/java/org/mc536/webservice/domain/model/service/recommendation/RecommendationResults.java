package org.mc536.webservice.domain.model.service.recommendation;

import java.util.List;

public class RecommendationResults<T> {

    private Long timeTaken;

    private List<Recommendation<T>> results;

    public RecommendationResults() {}

    public RecommendationResults(Long timeTaken, List<Recommendation<T>> results) {
        this.timeTaken = timeTaken;
        this.results = results;
    }

    public Long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public List<Recommendation<T>> getResults() {
        return results;
    }

    public void setResults(List<Recommendation<T>> results) {
        this.results = results;
    }
}
