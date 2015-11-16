package org.mc536.webservice.domain.model.service;

import org.mc536.webservice.domain.model.dao.CompanyDAO;
import org.mc536.webservice.domain.model.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyService {

    @Autowired
    private CompanyDAO companyDAO;

    public Company createCompany(String name){
        Company company = new Company();
        company.setName(name);

        try{
            companyDAO.create(company);
            return company;
        } catch (DuplicateKeyException e){
            throw new IllegalArgumentException("This Company is already registered: "+name);
        }
    }

    public Company updateCompany(Company newCompany){
        try{
            companyDAO.update(newCompany);
            return newCompany;
        } catch (DuplicateKeyException e){
            throw new IllegalArgumentException("This Company is already registered: "+newCompany.getName());
        }
    }

    public void delete(Integer id) {
        companyDAO.delete(id);
    }

    public List<Company> findAll(){ return companyDAO.findAll(); }

    public Company findById(Integer id){ return companyDAO.findById(id); }

    public Company findByName(String name) {
        return companyDAO.findByName(name);
    }

    public boolean exists(String name) {
        return findByName(name) != null;
    }
}
