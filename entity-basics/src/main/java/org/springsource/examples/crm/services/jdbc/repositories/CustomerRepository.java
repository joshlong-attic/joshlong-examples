package org.springsource.examples.crm.services.jdbc.repositories;

import org.springsource.examples.crm.model.Customer;

public interface CustomerRepository {

  Customer saveCustomer(Customer customer) ;

  Customer getCustomerById(long id);

}
