package org.mc536.webservice.domain.model.dao.mysql.company;

import org.mc536.webservice.domain.model.entity.Company;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRowMapper implements RowMapper<Company>{
    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company();
        company.setId(rs.getInt(CompanyColumn.ID));
        company.setName(rs.getString(CompanyColumn.NAME));
        company.setWebsite(rs.getString(CompanyColumn.WEBSITE));
        company.setIndustry(rs.getString(CompanyColumn.INDUSTRY));
        company.setNumberOfRatings(rs.getInt(CompanyColumn.NUMBEROFRATINGS));
        company.setLogo(rs.getString(CompanyColumn.LOGO));
        company.setOverallRating(rs.getDouble(CompanyColumn.OVERALLRATING));
        company.setCultureAndValuesRating(rs.getDouble(CompanyColumn.CULTUREANDVALUERATING));
        company.setSeniorLeadershipRating(rs.getDouble(CompanyColumn.SENIORLEADERSHIPRATING));
        company.setCompensationAndBenefitsRating(rs.getDouble(CompanyColumn.COMPESATIONANDBENEFITSRATING));
        company.setCareerOpportunitiesRating(rs.getDouble(CompanyColumn.CAREEROPPORTUNITIESRATING));
        company.setWorkLifeBalanceRating(rs.getDouble(CompanyColumn.WORKLIFEBALANCERATING));
        company.setRecomendToFriend(rs.getDouble(CompanyColumn.RECOMENDTOFRIEND));
        company.setCEOAproval(rs.getInt(CompanyColumn.CEOAPROVAL));
        return company;
    }
}
