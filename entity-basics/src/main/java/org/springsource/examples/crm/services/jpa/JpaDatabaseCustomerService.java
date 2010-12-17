package org.springsource.examples.crm.services.jpa;

import org.springsource.examples.crm.model.Customer;
import org.springsource.examples.crm.services.CustomerService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaDatabaseCustomerService implements CustomerService {


    @Override
    public Customer getCustomerById(long id) {
        return null;
    }

    @Override
    public Customer createCustomer(String fn, String ln) {
        return null;
    }
}
