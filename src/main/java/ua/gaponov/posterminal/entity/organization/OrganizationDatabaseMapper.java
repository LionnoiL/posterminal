package ua.gaponov.posterminal.entity.organization;

import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
public class OrganizationDatabaseMapper implements Mapper<Organization> {

    @Override
    public Organization map(ResultSet rs) {
        try {
            Organization organization = new Organization();
            organization.setGuid(rs.getString("org_guid"));
            organization.setName(rs.getString("org_name"));
            organization.setCode(rs.getString("code"));
            organization.setRroName(rs.getString("rro_name"));
            organization.setRroToken(rs.getString("rro_token"));
            organization.setRroActive(rs.getBoolean("rro_active"));

            return organization;
        } catch (SQLException e) {
            new MapperException("Error map organization");
        }
        return null;
    }

}
