package org.springsource.examples.crm.services.jpa;

import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.crm.model.Product;
import org.springsource.examples.crm.services.ProductService;

public class JpaDatabaseProductService implements ProductService {

    private JpaTemplate jpaTemplate ;

    public void setJpaTemplate(JpaTemplate jpaTemplate) {
        this.jpaTemplate = jpaTemplate;
    }

    public Product getProductById(long id) {
        return this.jpaTemplate.find( Product.class, id);
    }

    @Transactional
    public Product createProduct(String title, String desc, double price) {
        Product product = new Product() ;
        product.setDescription(desc);
        product.setName(title);
        product.setPrice(price);
        this.jpaTemplate.persist(product);
        this.jpaTemplate.refresh(product);
        return product;
    }
}
