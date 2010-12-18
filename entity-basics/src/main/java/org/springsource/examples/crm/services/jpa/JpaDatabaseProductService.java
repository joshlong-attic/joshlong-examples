package org.springsource.examples.crm.services.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.crm.model.Product;
import org.springsource.examples.crm.services.ProductService;

@Service
public class JpaDatabaseProductService implements ProductService {

    @Autowired private JpaTemplate jpaTemplate;

    public Product getProductById(long id) {
        return this.jpaTemplate.find(Product.class, id);
    }

    @Transactional
    public Product createProduct(String title, String desc, double price) {
        Product product = new Product();
        product.setDescription(desc);
        product.setName(title);
        product.setPrice(price);
        this.jpaTemplate.persist(product);
        this.jpaTemplate.refresh(product);
        return product;
    }
}
