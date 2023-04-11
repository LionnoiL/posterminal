package ua.gaponov.posterminal.organization;

import java.util.List;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

/**
 *
 * @author wmcon
 */
public class OrganizationService {
    
    public static List<Organization> getAll(){
       return new SqlHelper<Organization>().getAll("SELECT * FROM organization", new OrganizationMapper());
    }
    
    public static Organization getByGuid(String guid){
        StatementParameters<String, String> parameters = new StatementParameters<>(guid);
        return new SqlHelper<Organization>().getOne("select * from organization where org_guid = ?",
                parameters,
                new OrganizationMapper());
    }
    
    public static void deleteAll(){
        SqlHelper.execSql("delete from organization");
    }
}
