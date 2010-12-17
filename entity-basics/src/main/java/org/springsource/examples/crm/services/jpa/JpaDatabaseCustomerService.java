package org.springsource.examples.crm.services.jpa;

import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.crm.model.Customer;
import org.springsource.examples.crm.services.CustomerService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaDatabaseCustomerService implements CustomerService {

    private JpaTemplate jpaTemplate;

    public void setJpaTemplate(JpaTemplate jpaTemplate) {
        this.jpaTemplate = jpaTemplate;
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(long id) {
        return this.jpaTemplate.find(Customer.class, id);
    }

    @Transactional
    public Customer createCustomer(String fn, String ln) {
        Customer c = new Customer();
        c.setFirstName(fn);
        c.setLastName(ln);
        this.jpaTemplate.persist(c);
        //this.jpaTemplate.refresh(c);
        return c;
    }
}