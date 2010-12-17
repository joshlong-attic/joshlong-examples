package org.springsource.examples.crm.services.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springsource.examples.crm.services.JdbcDatabaseCustomerService;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class CrmConfiguration {

    /*dataSource.url=jdbc:postgresql://127.0.0.1/crm
dataSource.driverClassName=org.postgresql.Driver
dataSource.dialect=org.hibernate.dialect.PostgreSQLDialect
dataSource.user=crm
dataSource.password=crm*/

    @PostConstruct
    public void setup () throws Throwable  {
        System.out.println( "Setup() " + CrmConfiguration.class.getName()) ;
    }

    @Value("${dataSource.driverClassName}") private String driverName;

    @Value("${dataSource.url}") private String url;

    @Value("${dataSource.user}") private String user ;

    @Value("${dataSource.password}") private String password ;

    @Bean
    public DataSource dataSource(){
        //org.postgresql.Driver.class
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource( );
        return null ;
    }

    @Bean
    public JdbcTemplate jdbcTemplate (){
        return new JdbcTemplate( this.dataSource()) ;
    }

    @Bean
    public JdbcDatabaseCustomerService customerService(){
        JdbcDatabaseCustomerService jdbcDatabaseCustomerService = new JdbcDatabaseCustomerService();
        jdbcDatabaseCustomerService.setJdbcTemplate( this.jdbcTemplate()) ;
        return jdbcDatabaseCustomerService ;
    }

}
