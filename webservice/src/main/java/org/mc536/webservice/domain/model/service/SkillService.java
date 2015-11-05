package org.mc536.webservice.domain.model.service;

import org.mc536.webservice.domain.model.dao.SkillDAO;
import org.mc536.webservice.domain.model.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillDAO skillDAO;

    public Skill createSkill(String name) {
        Skill skill = new Skill();
        skill.setName(name);

        try {
            skillDAO.create(skill);
            return skill;
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("There's already a skill with name=" + name);
        }
    }

    public Skill updateSkill(Integer id, String name) {
        Skill skill = skillDAO.findById(id);
        skill.setName(name);

        try {
            skillDAO.update(skill);
            return skill;
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("There's already a skill with name=" + name);
        }
    }

    public List<Skill> findAll() {
        return skillDAO.findAll();
    }

    public Skill findById(Integer id) {
        return skillDAO.findById(id);
    }

    public Skill findByName(String name) {
        return skillDAO.findByName(name);
    }

    public boolean exists(String name) {
        return findByName(name) != null;
    }

    public void delete(Integer id) {
        skillDAO.delete(id);
    }
}
