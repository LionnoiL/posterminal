package ua.gaponov.posterminal.entity.organization;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrganizationService {

    public static List<Organization> getAll() {
        return new SqlHelper<Organization>().getAll("SELECT * FROM organization", new OrganizationDatabaseMapper());
    }

    public static Organization getByGuid(String guid) {
        StatementParameters<String> parameters = StatementParameters.build(guid);
        return new SqlHelper<Organization>().getOne("select * from organization where org_guid = ?",
                parameters,
                new OrganizationDatabaseMapper());
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
            StatementParameters parameters = StatementParameters.build(
                    organization.getCode(),
                    organization.getName(),
                    organization.getGuid(),
                    organization.getRroName(),
                    organization.getRroToken(),
                    organization.isRroActive()
            );
            String sql = """
                    insert into organization (code, org_name, org_guid, rro_name, rro_token, rro_active
                    )
                    values
                    (?, ?, ?, ?, ?, ?)
                    """;
            new SqlHelper<Organization>().execSql(sql, parameters);
        }
    }

    private static void update(Organization organization) throws SQLException {
        if (!organization.getGuid().isEmpty() && !organization.getName().isEmpty()) {
            StatementParameters parameters = StatementParameters.build(
                    organization.getCode(),
                    organization.getName(),
                    organization.getRroName(),
                    organization.getRroToken(),
                    organization.isRroActive(),
                    organization.getGuid()
            );
            String sql = """
                    update organization set code= ?, org_name = ?, rro_name = ?, rro_token = ?, rro_active = ?
                    where org_guid = ?
                    """;
            new SqlHelper<Organization>().execSql(sql, parameters);
        }
    }
}
