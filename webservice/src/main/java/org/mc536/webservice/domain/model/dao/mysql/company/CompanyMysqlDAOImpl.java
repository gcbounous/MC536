package org.mc536.webservice.domain.model.dao.mysql.company;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.commons.lang3.Validate;
import org.mc536.webservice.domain.model.dao.CompanyDAO;
import org.mc536.webservice.domain.model.entity.Company;
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

import static org.mc536.webservice.domain.model.dao.mysql.company.CompanyMysqlQueries.*;

@Repository
public class CompanyMysqlDAOImpl implements CompanyDAO{

    private static final int NAME_LENGTH = 40;

    private static final String NAME_INVALID_CHARS = "\\s";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SqlUpdate insertCommand;

    private SqlUpdate updateCommand;

    private final RowMapper<Company> ROW_MAPPER = new CompanyRowMapper();

    @Override
    public Company create(final Company company) {
        Validate.notNull(company, "Cannot create a null company");
        Validate.isTrue(company.getId() == null, "Cannot specify an Id for a new company");
        validateName(company.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertCommand().update(new Object[]{company.getName()}, keyHolder);

        company.setId(keyHolder.getKey().intValue());
        return company;
    }

    private SqlUpdate insertCommand() {
        if (insertCommand == null) {
            insertCommand = new SqlUpdate(dataSource, INSERT_COMPANY_QUERY);
            insertCommand.declareParameter(new SqlParameter(Types.VARCHAR));
            insertCommand.setReturnGeneratedKeys(true);
            insertCommand.setGeneratedKeysColumnNames("Id");
            insertCommand.compile();
        }
        return insertCommand;
    }

    @Override
    public void update(Company company) {
        Validate.notNull(company, "Cannot create a null company");
        Validate.notNull(company.getId(), "Cannot update a company with no Id");
        validateName(company.getName());

        updateCommand().update(company.getName(), company.getId());
    }

    private SqlUpdate updateCommand() {
        if (updateCommand == null) {
            updateCommand = new SqlUpdate(dataSource, UPDATE_COMPANY_QUERY);
            updateCommand.declareParameter(new SqlParameter(Types.VARCHAR));
            updateCommand.declareParameter(new SqlParameter(Types.INTEGER));
            updateCommand.compile();
        }
        return updateCommand;
    }

    @Override
    public List<Company> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, ROW_MAPPER);
    }

    @Override
    public Company findById(Integer id) {
        Validate.notNull(id, "Cannot find company with null Id");

        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, new Object[]{id}, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Company findByName(String name) {
        Validate.notBlank(name, "Cannot find company with blank name");

        try {
            return jdbcTemplate.queryForObject(FIND_BY_NAME_QUERY, new Object[]{name}, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        int affectedRows = jdbcTemplate.update(DELETE_COMPANY_QUERY, id);
        Validate.isTrue(affectedRows > 0, "Cannot find company with Id=" + id);
    }

    private void validateName(String name) {
        name = name.trim();

        Validate.notBlank(name, "Company name cannot be blank");
        Validate.isTrue(name.length() <= NAME_LENGTH, "Company names should be no longer than " + NAME_LENGTH + " characters");
        Validate.isTrue(!name.matches(NAME_INVALID_CHARS), "Company name contains invalid characters");
    }
}
