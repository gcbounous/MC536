package org.mc536.webservice.domain.model.dao.mysql.skill;

import static org.mc536.webservice.domain.model.dao.mysql.skill.SkillColumn.*;

public class SkillMysqlQueries {

    public static final String TABLE_NAME = "Skill";

    public static final String INSERT_SKILL_QUERY = "INSERT INTO " + TABLE_NAME + " (" + NAME + ")" +
            " VALUES (?)";

    public static final String UPDATE_SKILL_QUERY = "UPDATE " + TABLE_NAME + " SET " + NAME + " = ?" +
            " WHERE Id = ?";

    public static final String FIND_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;

    public static final String FIND_BY_ID_QUERY = "SELECT * FROM " + TABLE_NAME +
            " WHERE " + ID + " = ?";

    public static final String FIND_BY_NAME_QUERY = "SELECT * FROM " + TABLE_NAME +
            " WHERE " + NAME + " = ?";

    public static final String DELETE_SKILL_QUERY = "DELETE FROM " + TABLE_NAME +
            " WHERE " + ID + " = ?";
}
