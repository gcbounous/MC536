package org.mc536.webservice.domain.model.dao.mysql.company;

import static org.mc536.webservice.domain.model.dao.mysql.company.CompanyColumn.*;

public class CompanyMysqlQueries {

    public static final String TABLE_NAME = "Company";

    public static final String INSERT_COMPANY_QUERY = "INSERT INTO " + TABLE_NAME + " (" + NAME + ")" +
            " VALUES (?)";

    public static final String UPDATE_COMPANY_QUERY = "UPDATE " + TABLE_NAME + " SET " + NAME + " = ?" +
            " WHERE Id = ?";

    public static final String FIND_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;

    public static final String FIND_BY_ID_QUERY = "SELECT * FROM " + TABLE_NAME +
            " WHERE " + ID + " = ?";

    public static final String FIND_BY_NAME_QUERY = "SELECT * FROM " + TABLE_NAME +
            " WHERE " + NAME + " = ?";

    public static final String DELETE_COMPANY_QUERY = "DELETE FROM " + TABLE_NAME +
            " WHERE " + ID + " = ?";
}
