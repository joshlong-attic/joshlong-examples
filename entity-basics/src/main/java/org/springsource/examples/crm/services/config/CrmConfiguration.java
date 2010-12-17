package org.springsource.examples.crm.services.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springsource.examples.crm.services.jdbc.JdbcDatabaseCustomerService;

import javax.sql.DataSource;

@Configuration
public class CrmConfiguration {

    @Value("${dataSource.driverClassName}")
    private String driverName;

    @Value("${dataSource.url}")
    private String url;

    @Value("${dataSource.user}")
    private String user;

    @Value("${dataSource.password}")
    private String password;

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(this.dataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setPassword(this.password);
        simpleDriverDataSource.setUrl(this.url);
        simpleDriverDataSource.setUsername(this.user);
        simpleDriverDataSource.setDriverClass(org.h2.Driver.class);
        return simpleDriverDataSource;
    }

    /*@Bean
    public DataSource dataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setPassword(this.password);
        simpleDriverDataSource.setUrl(this.url);
        simpleDriverDataSource.setUsername(this.user);
        simpleDriverDataSource.setDriverClass(org.h2.Driver.class);
        return simpleDriverDataSource;
    }
    */
    /*

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        String driverClassName = org.h2.Driver.class.getName();
        basicDataSource.setDriverClassName(driverClassName);
        basicDataSource.setUrl(this.url);
        basicDataSource.setPassword(this.password);
        basicDataSource.setUsername(this.user);
        return basicDataSource;
    }
    */


    @Bean
    public JdbcTemplate jdbcTemplate() {
        DataSource ds = dataSource();
        return new JdbcTemplate(ds);
    }

    @Bean
    public JdbcDatabaseCustomerService customerService() {
        JdbcDatabaseCustomerService jdbcDatabaseCustomerService = new JdbcDatabaseCustomerService();
        jdbcDatabaseCustomerService.setJdbcTemplate(this.jdbcTemplate());
        return jdbcDatabaseCustomerService;
    }

}
