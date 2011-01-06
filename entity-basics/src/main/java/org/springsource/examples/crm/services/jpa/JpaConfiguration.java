package org.springsource.examples.crm.services.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springsource.examples.crm.services.config.CrmConfiguration;

import javax.persistence.EntityManagerFactory;

/**
 * sets up JPA machinery
 *
 * @author Josh Long
 */
@Configuration
public class JpaConfiguration extends CrmConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(this.dataSource());
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public JpaTemplate jpaTemplate() {
        EntityManagerFactory entityManagerFactory = entityManagerFactory().getObject();
        return new JpaTemplate(entityManagerFactory);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        EntityManagerFactory entityManagerFactory = entityManagerFactory().getObject();
        return new JpaTransactionManager(entityManagerFactory);
    }

}
