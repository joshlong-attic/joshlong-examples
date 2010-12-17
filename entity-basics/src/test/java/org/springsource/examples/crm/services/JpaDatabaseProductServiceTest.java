package org.springsource.examples.crm.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springsource.examples.crm.model.Customer;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/jdbc-services.xml"})
public class JpaDatabaseProductServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired private CustomerService customerService;

    private String firstName = "John";
    private String lastName = "Doe";

    @Test
    public void testCreatingACustomer() throws Throwable {
        Customer customer = customerService.createCustomer(this.firstName, this.lastName);
        assertNotNull("the customer can't be null", customer);
    }

}
