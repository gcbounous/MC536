package org.mc536.webservice.domain.model.dao;

import org.mc536.webservice.domain.model.entity.Skill;

import java.util.List;

public interface SkillDAO {

    Skill create(Skill skill);

    void update(Skill skill);

    List<Skill> findAll();

    Skill findById(Integer id);

    Skill findByName(String name);

    void delete(Integer id);
}
