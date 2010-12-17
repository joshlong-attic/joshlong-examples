package org.springsource.examples.crm.services.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springsource.examples.crm.model.Customer;
import org.springsource.examples.crm.services.CustomerService;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.Arrays;

/**
 * simple implementation of {@link org.springsource.examples.crm.services.CustomerService} that uses straight JDBC
 *
 * @author Josh Long
 */
@Service
public class JdbcDatabaseCustomerService implements CustomerService {

    @PostConstruct
    public void start() throws Throwable {
        System.out.println("start()  ");
    }

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer getCustomerById(long id) {
        return jdbcTemplate.queryForObject(
                " SELECT id, first_name, last_name FROM customer WHERE id = ? ", this.customerRowMapper, id);
    }


    public Customer createCustomer(final String fn, final String ln) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
            "INSERT INTO customer( first_name, last_name) VALUES ( ? , ? )", new int[]{Types.VARCHAR,Types.VARCHAR});
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        preparedStatementCreatorFactory.setGeneratedKeysColumnNames(new String[]{"id"});

        jdbcTemplate.update( preparedStatementCreatorFactory.newPreparedStatementCreator(Arrays.asList(fn,ln)),holder );

        Number id = holder.getKey();

        return this.getCustomerById(id.longValue());
    }


    /**
     * the {@link RowMapper} that handles building {@link Customer} objects
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
