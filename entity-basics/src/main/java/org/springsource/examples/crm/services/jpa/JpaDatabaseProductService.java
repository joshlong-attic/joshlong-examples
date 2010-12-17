package org.springsource.examples.crm.services.jpa;

import org.springsource.examples.crm.model.Product;
import org.springsource.examples.crm.services.ProductService;

/**
 *
 */
public class JpaDatabaseProductService implements ProductService {
    @Override
    public Product getProductById(long id) {
        return null;
    }

    @Override
    public Product createProduct(String title, String desc, double price) {
        return null;
    }
}
