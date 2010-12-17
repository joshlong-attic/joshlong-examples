package org.springsource.examples.crm.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springsource.examples.crm.model.Customer;
import static org.junit.Assert.*;

/**
 * tests {@link org.springsource.examples.crm.services.jdbc.JdbcDatabaseCustomerService}
 *
 * @author  Josh Long
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/jdbc-services.xml"})
public class JdbcDatabaseCustomerServiceTest   {

    // static vars we can trust
    private  String firstName = "John" ;
    private String lastName = "Doe";

    /**
     * injected reference of a {@link org.springsource.examples.crm.services.jdbc.JdbcDatabaseCustomerService}
     */
    @Autowired private CustomerService customerService ;

    @Test
    public  void testCustomerService() throws Throwable {
        Customer customer = this.customerService.createCustomer( this.firstName, this.lastName );
        assertNotNull(customer);
    }



}
