package com.joshlong.examples.activiti;

import org.activiti.DbSchemaStrategy;
import org.activiti.ProcessEngine;
import org.activiti.impl.cfg.spring.ProcessEngineFactoryBean;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Josh Long
 */
@Configuration
public class ActivitiConfig {

    private Collection<String> resources = Arrays.asList(
            "classpath:/processes/helloworld.bpmn20.xml");


    @Bean
    public Resource[] processResources() throws Throwable {
        List<Resource> res = new ArrayList<Resource>();
        for (String r : this.resources)
            res.add(new ClassPathResource(r));
        return res.toArray(new Resource[res.size()]);
    }

    @Bean
    public ProcessEngine processEngine() throws Throwable {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setDataSource(this.dataSource());
        processEngineFactoryBean.setTransactionManager(this.platformTransactionManager());
        processEngineFactoryBean.setDbSchemaStrategy(DbSchemaStrategy.DROP_CREATE);
        processEngineFactoryBean.setProcessResources(processResources());
        return processEngineFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() throws Throwable {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(this.dataSource());
        dataSourceTransactionManager.setNestedTransactionAllowed(true);
        dataSourceTransactionManager.afterPropertiesSet();
        return dataSourceTransactionManager;
    }

    @Bean
    public DataSource dataSource() throws Throwable {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:h2:tcp://localhost/~/activiti_example");
        basicDataSource.setDriverClassName(org.h2.Driver.class.getName());
        basicDataSource.setUsername("sa");
        basicDataSource.setPassword("");
        return basicDataSource;
    }


}
