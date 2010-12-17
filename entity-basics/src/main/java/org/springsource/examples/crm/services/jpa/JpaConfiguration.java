package org.springsource.examples.crm.services.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springsource.examples.crm.services.CustomerOrderService;
import org.springsource.examples.crm.services.CustomerService;
import org.springsource.examples.crm.services.ProductService;
import org.springsource.examples.crm.services.config.CrmConfiguration;

import javax.persistence.EntityManagerFactory;

/**
 * @author Josh Long
 */
@Configuration
public class JpaConfiguration extends CrmConfiguration {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(this.dataSource());
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public JpaTemplate jpaTemplate() {
        JpaTemplate jpaTemplate = new JpaTemplate(this.entityManagerFactory);
        return jpaTemplate;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(this.entityManagerFactory);
    }

    @Bean
    public CustomerService customerService() {
        JpaDatabaseCustomerService customerService = new JpaDatabaseCustomerService();
        customerService.setJpaTemplate(jpaTemplate());
        return customerService;
    }

    @Bean
    public ProductService productService() {
        JpaDatabaseProductService productService = new JpaDatabaseProductService();
        productService.setJpaTemplate( jpaTemplate());
        return productService;
    }

    @Bean
    public CustomerOrderService customerOrderService() {
        JpaDatabaseCustomerOrderService customerOrderService = new JpaDatabaseCustomerOrderService();
        customerOrderService.setCustomerService(this.customerService());
        customerOrderService.setProductService( this.productService() );
        customerOrderService.setJpaTemplate( jpaTemplate());
        return customerOrderService;
    }
}
