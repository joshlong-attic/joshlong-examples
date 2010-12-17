package org.springsource.examples.crm.services;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springsource.examples.crm.model.Customer;

import java.sql.*;

/**
 * simple implementation of {@link CustomerService} that uses straight JDBC
 *
 * @author Josh Long
 */
public class JdbcDatabaseCustomerService implements CustomerService {


    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer getCustomerById(long id) {
        return jdbcTemplate.queryForObject(
                " SELECT id, first_name, last_name FROM customer WHERE id = ? ", this.customerRowMapper, id);
    }

    @Override
    public Customer createCustomer( final String fn, final String ln) {

        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update( new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement( "INSERT INTO customer( first_name, last_name) VALUES ( ? , ? )" );
                ps.setString(1, fn);
                ps.setString(2,ln);
                return ps;
            }
        }, holder)   ;

        Number id = holder.getKey() ;
        return this.getCustomerById( id.longValue() );
    }


    /**
     * the {@link RowMapper} that handles building {@link Customer} objects
     */
    private RowMapper<Customer> customerRowMapper = new RowMapper<Customer>() {
        @Override
        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            return new Customer(id, firstName, lastName);
        }
    };

}
