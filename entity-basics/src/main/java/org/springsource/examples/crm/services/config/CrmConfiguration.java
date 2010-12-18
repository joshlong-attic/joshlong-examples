package org.springsource.examples.crm.services.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

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
    public DataSource dataSource(){
        SingleConnectionDataSource singleConnectionDataSource = new SingleConnectionDataSource();
        singleConnectionDataSource.setUrl(this.url);
        singleConnectionDataSource.setPassword(this.password);
        singleConnectionDataSource.setUsername( this.user);
        singleConnectionDataSource.setDriverClassName( org.h2.Driver.class.getName());
        return singleConnectionDataSource;

    }/*
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setPassword(this.password);
        simpleDriverDataSource.setUrl(this.url);
        simpleDriverDataSource.setUsername(this.user);
        simpleDriverDataSource.setDriverClass(org.h2.Driver.class);
        return simpleDriverDataSource;
    }*/
}
