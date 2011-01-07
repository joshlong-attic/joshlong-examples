package org.springsource.examples.crm.services.jdbc.repositories;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springsource.examples.crm.model.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

//@Repository
public class RawJdbcCustomerRepository implements CustomerRepository {

  protected final Log logger = LogFactory.getLog(getClass());

  private DataSource dataSource;

  private final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

  private final ThreadLocal<Integer> depthThreadLocal = new ThreadLocal<Integer>();

  public Customer saveCustomer(Customer customer) {

    String insertSql = " INSERT INTO customer( first_name, last_name) VALUES ( ? , ? )";
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = openThreadConnection();
      connection.setAutoCommit(false);

      preparedStatement = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, customer.getFirstName());
      preparedStatement.setString(2, customer.getLastName());

      customer = null;
      int rowsUpdated = preparedStatement.executeUpdate();
      if (rowsUpdated > 0) {
        ResultSet rsForKeys = preparedStatement.getGeneratedKeys();
        if (rsForKeys != null) {
          if (rsForKeys.next()) {
            Long id = rsForKeys.getLong(1);
            customer = getCustomerById(id);
          }
        }
        rsForKeys.close();
      }
      return customer;
    } catch (SQLException e) {
      if (connection != null) {
        try {
          connection.rollback();
        } catch (SQLException e1) {
          logError(e);
        }
      }
    } finally {

      closeThreadConnection();

      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          logError(e);
        }
      }
    }
    return null;
  }

  public Customer getCustomerById(long customerId) {
    Collection<Customer> customerArrayList = new ArrayList<Customer>();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = openThreadConnection();
      connection.setAutoCommit(true);

      preparedStatement = connection.prepareStatement("select * from customer where id = ?");
      preparedStatement.setLong(1, customerId);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        Customer c = new Customer();
        String fn = rs.getString("first_name");
        String ln = rs.getString("last_name");
        long id = rs.getLong("id");
        c.setFirstName(fn);
        c.setLastName(ln);
        c.setId(id);
        customerArrayList.add(c);
      }

      if (customerArrayList.size() > 0) {

        return customerArrayList.iterator().next();
      }
    } catch (SQLException e) {
      logError(e);
    } finally {
      closeThreadConnection();

      if (preparedStatement != null) try {
        preparedStatement.close();
      } catch (SQLException e) {
        logError(e);
      }
    }
    return null;
  }

  protected Integer incrementDepth() {
    Integer cnt = this.depthThreadLocal.get();
    if (cnt == null)
      this.depthThreadLocal.set(cnt = 0);
    this.depthThreadLocal.set(cnt + 1);
    Integer incrementedCount = this.depthThreadLocal.get();
    logger.debug("incrementedDepth: " + incrementedCount);
    return incrementedCount;
  }

  protected Integer decrementDepth() {
    Integer cnt = this.depthThreadLocal.get();
    if (cnt == null)
      this.depthThreadLocal.set(cnt = 0);
    this.depthThreadLocal.set(cnt - 1);
    Integer decrementedDepth = this.depthThreadLocal.get();
    logger.debug("decrementedDepth: " + decrementedDepth);
    return decrementedDepth;
  }

  protected Connection openThreadConnection() {
    if (connectionThreadLocal.get() == null) {
      try {
        logger.debug("the connectionThreadLocal is null, retrieving a new connection from the dataSource.");
        connectionThreadLocal.set(this.dataSource.getConnection());
      } catch (SQLException e) {
        logError(e);
      }
    } else {
      logger.debug("returning a cached connection from the connectionThreadLocal");
    }

    incrementDepth();

    return connectionThreadLocal.get();
  }

  protected void closeThreadConnection() {
    Connection connection = this.connectionThreadLocal.get();
    Integer decrementedDepth = decrementDepth();
    if (decrementedDepth == 0)
      if (connection != null) {
        try {
          logger.debug("connection.close() ");
          connection.close();
        } catch (SQLException e) {
          logError(e);
        } finally {
          this.connectionThreadLocal.remove();
        }
      }
  }

  /*


    public Customer getCustomerById(long customerId) {


    }

    public Customer createCustomer(String fn, String ln) {

    }

  */
  private void logError(Throwable th) {
    logger.error(ExceptionUtils.getFullStackTrace(th));
  }

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
}
