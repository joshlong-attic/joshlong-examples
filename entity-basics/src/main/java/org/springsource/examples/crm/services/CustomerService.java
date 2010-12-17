package org.springsource.examples.crm.services;

import org.springsource.examples.crm.model.Customer;

/**
 * Defines the contract to manipulate the entity model
 *
 * @author Josh Long
 */
public interface CustomerService {


    Customer getCustomerById(long id);


    /**
     * @param fn the first name
     * @param ln the last name
     * @return the {@link Customer}
     */
    Customer createCustomer(String fn, String ln);


}
