package org.springsource.examples.crm.services.jpa;

import org.springframework.orm.jpa.JpaTemplate;
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

public class JpaDatabaseCustomerOrderService implements CustomerOrderService {


    private JpaTemplate jpaTemplate;

    /**
     * we need a reference to the {@link JpaDatabaseProductService}
     */
    private CustomerService customerService;

    /**
     * we need a reference to a {@link JpaDatabaseProductService}
     */
    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setJpaTemplate(JpaTemplate jpaTemplate) {
        this.jpaTemplate = jpaTemplate;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Transactional
    public Purchase createPurchase(long customerId) {
        Purchase p = new Purchase();
        Customer c = this.customerService.getCustomerById(customerId);
        p.setCustomer(c);
        p.setTotal(0);
        this.jpaTemplate.persist(p);
        return p;
    }

    private void retabulate(Purchase p) {
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
        this.jpaTemplate.persist(lineItem);

        purchase.getLineItems().add(lineItem);
        retabulate(purchase);
        this.jpaTemplate.merge(purchase);

    }

    @Transactional
    public void checkout(long purchaseId) {
    }

    @PostConstruct
    void setup() throws Throwable {
        Assert.notNull(this.customerService, "'customerService' instance can't be null");
    }
}
