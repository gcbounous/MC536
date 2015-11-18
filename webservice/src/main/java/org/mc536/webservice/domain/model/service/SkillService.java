package org.mc536.webservice.domain.model.service;

import org.apache.commons.lang3.Validate;
import org.mc536.webservice.domain.model.dao.SkillDAO;
import org.mc536.webservice.domain.model.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private static final int NAME_LENGTH = 20;

    private static final String NAME_INVALID_CHARS = "\\s";

    @Autowired
    private SkillDAO skillDAO;

    public Skill createSkill(String name) {
        validateName(name);

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
        validateName(name);

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

    private void validateName(String name) {
        name = name.trim();

        Validate.notBlank(name, "Skill name cannot be blank");
        Validate.isTrue(name.length() <= NAME_LENGTH, "Skill names should be no longer than " + NAME_LENGTH + " characters");
        Validate.isTrue(!name.matches(NAME_INVALID_CHARS), "Skill name contains invalid characters");
    }
}
