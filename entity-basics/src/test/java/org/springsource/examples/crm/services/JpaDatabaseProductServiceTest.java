package org.springsource.examples.crm.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springsource.examples.crm.model.Customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/jpa-services.xml"})
public class JpaDatabaseProductServiceTest  {


    private String firstName = "John";

    private String lastName = "Doe";

    @Autowired
    private CustomerService customerService;

    @Test
    public void testCustomerService() throws Throwable {
        Customer customer = this.customerService.createCustomer(this.firstName, this.lastName);
        assertNotNull(customer);
        assertTrue(customer.getId() > 0);

        Customer customer2 = this.customerService.getCustomerById( customer.getId()) ;
        assertEquals(customer2.getFirstName(), this.firstName);
        assertEquals(customer2.getLastName(), this.lastName);
        assertEquals( customer.getId(), customer2.getId());

    }




}
