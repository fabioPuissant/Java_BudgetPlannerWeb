package be.pxl.student.api;

import be.pxl.student.bean.Label;
import be.pxl.student.bean.Payment;
import be.pxl.student.dao.LabelDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.dao.PaymentException;
import be.pxl.student.exceptions.LabelException;
import be.pxl.student.factories.BudgetPlannerDaoFactory;
import be.pxl.student.utils.ConnectionFactory;
import be.pxl.student.utils.ConnectionStringFactory;

import javax.ws.rs.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/Labels")
public class LabelApi {
    public LabelApi() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
    @GET
    @Produces("application/json")
    public List<Label> getAllLabels() throws SQLException, LabelException {
        Connection connection = getConnection();
        LabelDao dao = new BudgetPlannerDaoFactory(connection).getLabelDao();
        List<Label> labels = dao.getAllLabels();
        connection.close();

        return labels;
    }

    @GET
    @Path("/{id}")
    public Label getLabelById(@PathParam("id") int id) throws LabelException, SQLException {
        Connection connection = getConnection();
        LabelDao dao = new BudgetPlannerDaoFactory(connection).getLabelDao();
        Label label = dao.getById(id);
        connection.close();

        return label;
    }

    @GET
    @Path("/{id}/Payments")
    @Produces("application/json")
    public List<Payment> getAllPaymentsOfLabel(@PathParam("id") int id) throws LabelException, SQLException, PaymentException {
        Connection connection = getConnection();
        LabelDao labelDao = new BudgetPlannerDaoFactory(connection).getLabelDao();

        Label foundLabel = labelDao.getById(id);
        PaymentDao paymentDao = new BudgetPlannerDaoFactory(connection).getPaymentDao();
        List<Payment> payments = paymentDao.getPaymentsOfLabel(foundLabel.getId());
        connection.close();

        return payments;
    }

    @POST
    @Consumes("application/json")
    public Label addLabel(Label label) throws SQLException, LabelException {
        Connection connection = getConnection();
        LabelDao dao = new BudgetPlannerDaoFactory(connection).getLabelDao();
        Label addedLabel = dao.addLabel(label);
        connection.close();

        return addedLabel;
    }

    private Connection getConnection() throws SQLException {
        String connectionString = getConnectionString();
        return ConnectionFactory.getJdbcMysqlConnection(connectionString, "student","root");
    }

    private String getConnectionString() {
        return ConnectionStringFactory.getJdbcMysqlConnectionString("192.168.33.22", "StudentDB");
    }
}
