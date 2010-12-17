package org.springsource.examples.crm.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springsource.examples.crm.model.Customer;
import static org.junit.Assert.*;

/**
 * tests {@link JdbcDatabaseCustomerService}
 *
 * @author  Josh Long
 */
@ContextConfiguration(locations = {"jdbc-service.xml"})
public class JdbcDatabaseCustomerServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    // static vars we can trust
    private  String firstName = "John", lastName = "Doe";

    /**
     * injected reference of a {@link JdbcDatabaseCustomerService}
     */
    @Autowired private CustomerService customerService ;

    @Test
    public  void testCustomerService() throws Throwable {
        Customer customer = this.customerService.createCustomer( this.firstName, this.lastName );
        assertNotNull(customer);
    }



}
