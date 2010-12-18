package org.springsource.examples.crm.services.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.crm.model.Customer;
import org.springsource.examples.crm.services.CustomerService;

@Service
public class JpaDatabaseCustomerService implements CustomerService {

    private JpaTemplate jpaTemplate;

    @Transactional(readOnly = true)
    public Customer getCustomerById(long id) {
        return this.jpaTemplate.find(Customer.class, id);
    }

    @Transactional
    public Customer createCustomer(String fn, String ln) {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(fn);
        newCustomer.setLastName(ln);
        this.jpaTemplate.persist(newCustomer);
        return newCustomer;
    }

    @Autowired
    public void setJpaTemplate(JpaTemplate jpaTemplate) {
        this.jpaTemplate = jpaTemplate;
    }
}
