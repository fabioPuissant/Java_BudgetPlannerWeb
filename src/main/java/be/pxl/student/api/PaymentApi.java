package be.pxl.student.api;

import be.pxl.student.bean.Payment;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.dao.PaymentException;
import be.pxl.student.utils.ConnectionFactory;
import be.pxl.student.utils.ConnectionStringFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/Payments")
public class PaymentApi {
    public PaymentApi() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
    @GET
    @Produces("application/json")
    public List<Payment> getAllPayments() throws SQLException, PaymentException {
        Connection connection = getConnection();
        List<Payment> payments = getPaymentDao(connection).getAllPayments();
        connection.close();
        return payments;
    }

    @GET
    @Produces({"application/json", "text/plain"})
    @Path("/{id}")
    public Payment getPaymentById(@PathParam("id") int id) throws Exception {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = getConnection();
            return getPaymentDao(con).getById(id);
        } catch (ClassNotFoundException | SQLException | PaymentException exception) {
            throw new Exception(exception);
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }



    private Connection getConnection() throws SQLException {
        String connectionString =
                ConnectionStringFactory.getJdbcMysqlConnectionString("192.168.33.22", "StudentDB");

        return ConnectionFactory.getJdbcMysqlConnection(connectionString, "student", "root");
    }

    private PaymentDao getPaymentDao(Connection connection) {
        return new PaymentDao(connection);
    }
}
