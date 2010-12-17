package org.springsource.examples.crm.services;

import org.springsource.examples.crm.model.Product;

/**
 * the product service manages / manipulates {@link org.springsource.examples.crm.model.Product} inventory.
 *
 * @author Josh Long
 */
public interface ProductService {


    Product getProductById(long id);

    Product createProduct(String title, String desc, double price);

}
