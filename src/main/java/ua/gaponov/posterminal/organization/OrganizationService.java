package ua.gaponov.posterminal.organization;

import java.sql.SQLException;
import java.util.List;
import ua.gaponov.posterminal.database.Parametr;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

/**
 *
 * @author wmcon
 */
public class OrganizationService {

    public static List<Organization> getAll() {
        return new SqlHelper<Organization>().getAll("SELECT * FROM organization", new OrganizationMapper());
    }

    public static Organization getByGuid(String guid) {
        StatementParameters<String, String> parameters = new StatementParameters<>(guid);
        return new SqlHelper<Organization>().getOne("select * from organization where org_guid = ?",
                parameters,
                new OrganizationMapper());
    }

    public static void deleteAll() {
        SqlHelper.execSql("delete from organization");
    }

    public static void save(Organization organization) throws SQLException {
        Organization findOrganization = getByGuid(organization.getGuid());
        if (findOrganization == null) {
            insert(organization);
        } else {
            update(organization);
        }
    }

    private static void insert(Organization organization) throws SQLException {
        if (!organization.getGuid().isEmpty() && !organization.getName().isEmpty()) {
            StatementParameters<String, String> parameters
                    = new StatementParameters<>(organization.getCode(), organization.getName());
            parameters.add(new Parametr(organization.getGuid()));
            String sql = """
                     insert into organization (code, org_name, org_guid
                     ) 
                     values
                     (?, ?, ?)
                     """;
            new SqlHelper<Organization>().execSql(sql, parameters);
        }
    }

    private static void update(Organization organization) throws SQLException {
        if (!organization.getGuid().isEmpty() && !organization.getName().isEmpty()) {
            StatementParameters<String, String> parameters
                    = new StatementParameters<>(organization.getCode(), organization.getName());
            parameters.add(new Parametr(organization.getGuid()));
            String sql = """
                     update organization set code= ?, org_name = ?
                     where org_guid = ?
                     """;
            new SqlHelper<Organization>().execSql(sql, parameters);
        }
    }
}
