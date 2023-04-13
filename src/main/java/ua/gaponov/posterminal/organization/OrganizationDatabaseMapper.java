package ua.gaponov.posterminal.organization;

import java.sql.ResultSet;
import java.sql.SQLException;
import ua.gaponov.posterminal.database.Mapper;

/**
 *
 * @author wmcon
 */
public class OrganizationDatabaseMapper implements Mapper<Organization>{

    @Override
    public Organization map(ResultSet rs) {
        try {
            Organization organization = new Organization();
            organization.setGuid(rs.getString("org_guid"));
            organization.setName(rs.getString("org_name"));
            organization.setCode(rs.getString("code"));
 
            return organization;
        } catch (SQLException e) {
            new RuntimeException();
        }
        return null;
    }
    
}
