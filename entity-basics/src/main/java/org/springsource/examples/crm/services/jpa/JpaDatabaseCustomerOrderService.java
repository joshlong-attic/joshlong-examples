package org.springsource.examples.crm.services.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springsource.examples.crm.model.Customer;
import org.springsource.examples.crm.model.LineItem;
import org.springsource.examples.crm.model.Product;
import org.springsource.examples.crm.model.Purchase;
import org.springsource.examples.crm.services.CustomerOrderService;
import org.springsource.examples.crm.services.CustomerService;
import org.springsource.examples.crm.services.ProductService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class JpaDatabaseCustomerOrderService implements CustomerOrderService {

    private JpaTemplate jpaTemplate;

    private CustomerService customerService;

    private ProductService productService;

    @Transactional
    public Purchase createPurchase(long customerId) {
        Purchase purchase = new Purchase();
        Customer customer = this.customerService.getCustomerById(customerId);
        purchase.setCustomer(customer);
        purchase.setTotal(0);
        this.jpaTemplate.persist(purchase);
        return purchase;
    }

    private void recalculate(Purchase p) {
        double total = 0;
        Set<LineItem> lis = p.getLineItems();
        for (LineItem lineItem : lis)
            total += lineItem.getProduct().getPrice();
        p.setTotal(total);
    }

    public Purchase getPurchaseById(long purchaseId) {
        return this.jpaTemplate.find(Purchase.class, purchaseId);
    }

    @Transactional
    public void addProductToPurchase(long purchaseId, long productId) {
        Purchase purchase = getPurchaseById(purchaseId);
        Product product = productService.getProductById(productId);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setPurchase(purchase);
        jpaTemplate.persist(lineItem);

        purchase.getLineItems().add(lineItem);
        recalculate(purchase);
        jpaTemplate.merge(purchase);
    }

    @PostConstruct
    public void setup() throws Throwable {
        Assert.notNull(this.customerService, "'customerService' instance can't be null");
    }

    @Autowired
    public void setJpaTemplate(JpaTemplate jpaTemplate) {
        this.jpaTemplate = jpaTemplate;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
