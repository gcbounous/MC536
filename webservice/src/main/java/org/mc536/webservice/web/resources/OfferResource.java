package org.mc536.webservice.web.resources;

import org.mc536.webservice.domain.model.entity.Offer;
import org.mc536.webservice.domain.model.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferResource {

    @Autowired
    private OfferService offerService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Offer> all() {
        return offerService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public Offer create(@RequestParam("title") String title,
                        @RequestParam("description") String description,
                        @RequestParam("location") String location,
                        @RequestParam("url") String url,
                        @RequestParam("companyId") Integer companyId) {

        return offerService.postOffer(title, description, location, url, companyId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Offer update(@RequestParam("id") Integer id,
                        @RequestParam("title") String title,
                        @RequestParam("description") String description,
                        @RequestParam("location") String location,
                        @RequestParam("yurl") String url) {

        return offerService.updateOffer(id, title, description, location, url);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Offer findById(@PathVariable("id") Integer id) {
        return offerService.findById(id);
    }

    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
    public List<Offer> findByCompanyId(@PathVariable("id") Integer companyId) {
        return offerService.findByCompanyid(companyId);
    }

    @RequestMapping(value = "/skill/{skill}", method = RequestMethod.GET)
    public List<Offer> findBySkill(@PathVariable("skill") String skill) {
        return offerService.findBySkill(skill);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public void delete(@PathVariable("id") Integer id) {
        offerService.delete(id);
    }
}
