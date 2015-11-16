package org.mc536.webservice.web.resources;

import org.mc536.webservice.domain.model.entity.Company;
import org.mc536.webservice.domain.model.dao.CompanyDAO;
import org.mc536.webservice.domain.model.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyResource {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Company> all() {
        return companyService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public Company create(@RequestParam("name") String name) {
        return companyService.createCompany(name);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Company update(Company company) {

        return companyService.updateCompany(company);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Company findById(@PathVariable("id") Integer id) {
        return companyService.findById(id);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public Company findByName(@PathVariable("name") String name) {
        return companyService.findByName(name);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public void delete(@PathVariable("id") Integer id) {
        companyService.delete(id);
    }
}
