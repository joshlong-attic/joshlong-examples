package org.springsource.examples.crm.services.jdbc.repositories;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springsource.examples.crm.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

@Repository
public class JdbcTemplateCustomerRepository implements CustomerRepository, InitializingBean {


  @Value("${jdbc.sql.customers.queryById}")
  private String customerByIdQuery;

  @Value("${jdbc.sql.customers.insert}")
  private String insertCustomerQuery;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Customer saveCustomer(Customer customer) {
    GeneratedKeyHolder holder = new GeneratedKeyHolder();

    PreparedStatementCreatorFactory preparedStatementCreatorFactory =
        new PreparedStatementCreatorFactory(insertCustomerQuery, new int[]{Types.VARCHAR, Types.VARCHAR});
    preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
    preparedStatementCreatorFactory.setGeneratedKeysColumnNames(new String[]{"id"});

    PreparedStatementCreator psc = preparedStatementCreatorFactory.newPreparedStatementCreator(Arrays.asList(customer.getFirstName(), customer.getLastName()));
    jdbcTemplate.update(psc, holder);

    Number id = holder.getKey();

    return getCustomerById(id.longValue());
  }

  public Customer getCustomerById(long id) {
    return jdbcTemplate.queryForObject(customerByIdQuery, customerRowMapper, id);
  }

  public void afterPropertiesSet() throws Exception {
    Assert.notNull(this.jdbcTemplate, "the jdbcTemplate can't be null!");
    Assert.notNull(this.customerByIdQuery, "the customerByIdQuery can't be null");
    Assert.notNull(this.insertCustomerQuery, "the insertCustomerQuery can't be null");
  }

  /**
   * shared instance of a {@link RowMapper} that knows how to build a {@link Customer} record. These objects are stateless and can be cached.
   */
  private RowMapper<Customer> customerRowMapper = new RowMapper<Customer>() {

    public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
      long id = resultSet.getInt("id");
      String firstName = resultSet.getString("first_name");
      String lastName = resultSet.getString("last_name");
      return new Customer(id, firstName, lastName);
    }
  };
}
