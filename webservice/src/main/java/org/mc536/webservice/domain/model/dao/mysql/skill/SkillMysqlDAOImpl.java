package org.mc536.webservice.domain.model.dao.mysql.skill;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.commons.lang3.Validate;
import org.mc536.webservice.domain.model.dao.SkillDAO;
import org.mc536.webservice.domain.model.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

import static org.mc536.webservice.domain.model.dao.mysql.skill.SkillMysqlQueries.*;

@Repository
public class SkillMysqlDAOImpl implements SkillDAO {

    private static final int NAME_LENGTH = 20;

    private static final String NAME_INVALID_CHARS = "\\s";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SqlUpdate insertCommand;

    private SqlUpdate updateCommand;

    private final RowMapper<Skill> ROW_MAPPER = new SkillRowMapper();

    @Override
    public Skill create(final Skill skill) {
        Validate.notNull(skill, "Cannot create a null skill");
        Validate.isTrue(skill.getId() == null, "Cannot specify an Id for a new skill");
        validateName(skill.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertCommand().update(new Object[]{skill.getName()}, keyHolder);

        skill.setId(keyHolder.getKey().intValue());
        return skill;
    }

    private SqlUpdate insertCommand() {
        if (insertCommand == null) {
            insertCommand = new SqlUpdate(dataSource, INSERT_SKILL_QUERY);
            insertCommand.declareParameter(new SqlParameter(Types.VARCHAR));
            insertCommand.setReturnGeneratedKeys(true);
            insertCommand.setGeneratedKeysColumnNames("Id");
            insertCommand.compile();
        }
        return insertCommand;
    }

    @Override
    public void update(Skill skill) {
        Validate.notNull(skill, "Cannot create a null skill");
        Validate.notNull(skill.getId(), "Cannot update a skill with no Id");
        validateName(skill.getName());

        updateCommand().update(skill.getName(), skill.getId());
    }

    private SqlUpdate updateCommand() {
        if (updateCommand == null) {
            updateCommand = new SqlUpdate(dataSource, UPDATE_SKILL_QUERY);
            updateCommand.declareParameter(new SqlParameter(Types.VARCHAR));
            updateCommand.declareParameter(new SqlParameter(Types.INTEGER));
            updateCommand.compile();
        }
        return updateCommand;
    }

    @Override
    public List<Skill> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, ROW_MAPPER);
    }

    @Override
    public Skill findById(Integer id) {
        Validate.notNull(id, "Cannot find skill with null Id");

        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, new Object[]{id}, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Skill findByName(String name) {
        Validate.notBlank(name, "Cannot find skill with blank name");

        try {
            return jdbcTemplate.queryForObject(FIND_BY_NAME_QUERY, new Object[]{name}, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        int affectedRows = jdbcTemplate.update(DELETE_SKILL_QUERY, id);
        Validate.isTrue(affectedRows > 0, "Cannot find skill with Id=" + id);
    }

    private void validateName(String name) {
        name = name.trim();

        Validate.notBlank(name, "Skill name cannot be blank");
        Validate.isTrue(name.length() <= NAME_LENGTH, "Skill names should be no longer than " + NAME_LENGTH + " characters");
        Validate.isTrue(!name.matches(NAME_INVALID_CHARS), "Skill name contains invalid characters");
    }
}