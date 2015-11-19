package org.mc536.webservice.domain.model.service;

import org.apache.commons.lang3.Validate;
import org.mc536.webservice.domain.model.dao.SkillDAO;
import org.mc536.webservice.domain.model.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private static final String NAME_INVALID_CHARS = "\\s";

    @Autowired
    private SkillDAO skillDAO;

    public Skill createSkill(String name) {

        Skill skill = new Skill();
        skill.setName(normalizeName(name));

        try {
            skillDAO.create(skill);
            return skill;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Cannot create skill with name=" + name, e);
        }
    }

    public Skill updateSkill(Integer id, String name) {

        Skill skill = skillDAO.findById(id);
        skill.setName(normalizeName(name));

        try {
            skillDAO.update(skill);
            return skill;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("There's already a skill with name=" + name, e);
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

    public boolean exists(Integer id) {
        return findById(id) != null;
    }

    public boolean exists(String name) {
        return findByName(name) != null;
    }

    public void delete(Integer id) {
        skillDAO.delete(id);
    }

    private String normalizeName(String name) {
        Validate.notNull(name, "Name cannot be null");

        String normalized = name.trim().toLowerCase();

        Validate.notBlank(normalized, "Skill name cannot be blank");
        Validate.isTrue(!normalized.matches(NAME_INVALID_CHARS), "Skill name contains invalid characters");

        return normalized;
    }
}
