package org.springsource.examples.crm.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springsource.examples.crm.model.Customer;
import org.springsource.examples.crm.model.Product;
import org.springsource.examples.crm.model.Purchase;

import static org.junit.Assert.assertTrue;

/**
 * @author Josh Long
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/jpa-services.xml"})
public class JpaDatabaseCustomerOrderServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Test
    public void testAddingProducts() {
        Customer customer = this.customerService.createCustomer("A", "Customer");

        Purchase purchase = this.customerOrderService.createPurchase(customer.getId());

        double price1 = 12,
                price2 = 7.5;

        Product product1 = this.productService.createProduct("Widget1", "a widget that slices (but not dices)", price1),
                product2 = this.productService.createProduct("Widget2", "a widget that dices (but not slices)", price2);

        this.customerOrderService.addProductToPurchase(purchase.getId(), product1.getId());
        this.customerOrderService.addProductToPurchase(purchase.getId(), product2.getId());

        purchase = this.customerOrderService.getPurchaseById(purchase.getId());
        assertTrue(purchase.getTotal() == (price1 + price2));
    }
}
