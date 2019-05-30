package be.pxl.student.api;

import be.pxl.student.bean.Account;
import be.pxl.student.dao.AccountDao;
import be.pxl.student.exceptions.AccountException;
import be.pxl.student.factories.BudgetPlannerDaoFactory;
import be.pxl.student.utils.ConnectionFactory;
import be.pxl.student.utils.ConnectionStringFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/Accounts")
public class AccountApi {
    public AccountApi() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    @GET
    @Produces("application/json")
    public List<Account> getAllAccounts() throws SQLException, AccountException {
        Connection connection = getConnection();
        AccountDao dao = new BudgetPlannerDaoFactory(connection).getAccountDao();
        List<Account> accounts = dao.getAllAccounts();
        connection.close();
        return accounts;
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Account getAccountById(@PathParam("id") int id) throws SQLException {
        Connection connection = null;
        try{
            connection=getConnection();
            AccountDao dao = new BudgetPlannerDaoFactory(connection).getAccountDao();
            Account account = dao.getById(id);
            connection.close();
            return account;
        }
        catch (SQLException | AccountException exception){
            return null;
        }
        finally {
            if(connection != null){
                connection.close();
            }
        }

    }

    private Connection getConnection() throws SQLException {
        String connectionString =
                ConnectionStringFactory.getJdbcMysqlConnectionString("192.168.33.22", "StudentDB");
        return ConnectionFactory.getJdbcMysqlConnection(connectionString, "student", "root");
    }
}
