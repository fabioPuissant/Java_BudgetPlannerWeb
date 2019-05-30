package be.pxl.student.factories;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.LabelDao;
import be.pxl.student.dao.PaymentDao;

import java.sql.Connection;

public class BudgetPlannerDaoFactory {
    private Connection connection;

    public BudgetPlannerDaoFactory(Connection connection){
        this.connection = connection;
    }

    public AccountDao getAccountDao(){
        return new AccountDao(connection);
    }

    public LabelDao getLabelDao(){
        return new LabelDao(connection);
    }

    public PaymentDao getPaymentDao(){
        return new PaymentDao(connection);
    }
}
