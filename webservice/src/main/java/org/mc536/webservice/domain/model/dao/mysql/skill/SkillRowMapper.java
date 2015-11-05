package org.mc536.webservice.domain.model.dao.mysql.skill;

import org.mc536.webservice.domain.model.entity.Skill;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class SkillRowMapper implements RowMapper<Skill> {
    @Override
    public Skill mapRow(ResultSet rs, int rowNum) throws SQLException {
        Skill skill = new Skill();
        skill.setId(rs.getInt(SkillColumn.ID));
        skill.setName(rs.getString(SkillColumn.NAME));
        return skill;
    }
}
