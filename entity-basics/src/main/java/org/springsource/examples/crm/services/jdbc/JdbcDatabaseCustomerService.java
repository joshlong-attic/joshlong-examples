package org.springsource.examples.crm.services.jdbc;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springsource.examples.crm.model.Customer;
import org.springsource.examples.crm.services.CustomerService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

/**
 * simple implementation of {@link org.springsource.examples.crm.services.CustomerService} that uses straight JDBC
 *
 * @author Josh Long
 */
public class JdbcDatabaseCustomerService implements CustomerService, InitializingBean {

    private JdbcTemplate jdbcTemplate;

    @Value("${jdbc.sql.customers.queryById}")
    private String customerByIdQuery ;

    @Value("${jdbc.sql.customers.insert}")
    private String insertCustomerQuery;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(long id) {
        return jdbcTemplate.queryForObject(this.customerByIdQuery, this.customerRowMapper, id);
    }


    @Transactional
    public Customer createCustomer(String firstName, String lastName) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreatorFactory preparedStatementCreatorFactory =
                new PreparedStatementCreatorFactory(this.insertCustomerQuery, new int[]{Types.VARCHAR, Types.VARCHAR});
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        preparedStatementCreatorFactory.setGeneratedKeysColumnNames(new String[]{"id"});

        jdbcTemplate.update(preparedStatementCreatorFactory.newPreparedStatementCreator(Arrays.asList(firstName, lastName)), holder);

        Number id = holder.getKey();

        return this.getCustomerById(id.longValue());
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.jdbcTemplate, "the jdbcTemplate can't be null!");
        Assert.notNull( this.customerByIdQuery ,"the customerByIdQuery can't be null");
        Assert.notNull( this.insertCustomerQuery,  "the insertCustomerQuery can't be null");
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
