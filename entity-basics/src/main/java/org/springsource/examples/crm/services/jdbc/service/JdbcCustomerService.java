package org.springsource.examples.crm.services.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.crm.model.Customer;
import org.springsource.examples.crm.services.CustomerService;
import org.springsource.examples.crm.services.jdbc.repositories.CustomerRepository;

@Service
public class JdbcCustomerService implements CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Transactional(readOnly = true)
  public Customer getCustomerById(long id) {
    return this.customerRepository.getCustomerById(id);
  }

  @Transactional
  public Customer createCustomer(String fn, String ln) {
    Customer customer = new Customer(fn, ln);
    return this.customerRepository.saveCustomer(customer);
  }
}
