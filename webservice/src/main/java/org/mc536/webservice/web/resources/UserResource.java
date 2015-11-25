package org.mc536.webservice.web.resources;

import java.util.List;

import org.mc536.webservice.domain.model.entity.OfferRating;
import org.mc536.webservice.domain.model.entity.User;
import org.mc536.webservice.domain.model.entity.Offer;
import org.mc536.webservice.domain.model.service.UserService;
import org.mc536.webservice.domain.model.service.recommendation.Recommendation;
import org.mc536.webservice.domain.model.service.recommendation.RecommendationServiceV1;
import org.mc536.webservice.domain.model.service.recommendation.RecommendationServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private RecommendationServiceV1 recommendationServiceV1;

    @Autowired
    private RecommendationServiceV2 recommendationServiceV2;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> all() {
        return userService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public User create(@RequestParam("name") String name) {
        return userService.createUser(name);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public User update(@RequestParam("id") Integer id,
                       @RequestParam("name") String name) {

        return userService.updateUser(id, name);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public void delete(@PathVariable("id") Integer id) {
        userService.delete(id);
    }

    @RequestMapping(value = "/recommend/{id}/offers_by_skills", method = RequestMethod.GET)
    public List<Offer> recommendedOffersBySkills(@PathVariable("id") Integer id,
                                                 @RequestParam(name = "limit", required = false) Integer limit) {

        return userService.recommendedOffers(id, limit);
    }

    @RequestMapping(value = "/recommend/{id}/offers_by_ratings/v1", method = RequestMethod.GET)
    public List<Recommendation<Offer>> recommendedOffersByRatingsV1(@PathVariable("id") Integer id,
                                                                    @RequestParam(name = "limit", required = false) Integer limit) {

        return recommendationServiceV1.recommendOffers(id, limit);
    }

    @RequestMapping(value = "/recommend/{id}/offers_by_ratings/v2", method = RequestMethod.GET)
    public List<Recommendation<Offer>> recommendedOffersByRatingsV2(@PathVariable("id") Integer id,
                                                                    @RequestParam(name = "limit", required = false) Integer limit) {

        return recommendationServiceV2.recommendOffers(id, limit);
    }

    @RequestMapping(value = "/like/{userId}", method = RequestMethod.GET)
    public OfferRating likeOffer(@PathVariable("userId") Integer userId,
                                 @RequestParam("offerId") Integer offerId) {

        return userService.likeOffer(userId, offerId);
    }

    @RequestMapping(value = "/dislike/{userId}", method = RequestMethod.GET)
    public OfferRating dislikeOffer(@PathVariable("userId") Integer userId,
                                    @RequestParam("offerId") Integer offerId) {

        return userService.dislikeOffer(userId, offerId);
    }

    @RequestMapping(value = "/unrate/{userId}", method = RequestMethod.GET)
    public void unrateOffer(@PathVariable("userId") Integer userId,
                            @RequestParam("offerId") Integer offerId) {

        userService.unrateOffer(userId, offerId);
    }

    @RequestMapping(value = "/ratings/{id}", method = RequestMethod.GET)
    public List<OfferRating> offerRatings(@PathVariable("id") Integer id) {
        return userService.offerRatings(id);
    }
}