package com.joshlong.examples.activiti.spring.core;

import org.activiti.DbSchemaStrategy;
import org.activiti.ProcessEngine;
import org.activiti.ProcessService;
import org.activiti.impl.cfg.spring.ProcessEngineFactoryBean;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.activiti.el.SpringBeanFactoryELResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.el.ELResolver;
import javax.sql.DataSource;


@SuppressWarnings("unused")
@Configuration
public class ActivitiConfiguration {

	protected DataSource buildDataSource(String url, Class className, String user, String pw) {
		TransactionAwareDataSourceProxy dataSource = new TransactionAwareDataSourceProxy();
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setAccessToUnderlyingConnectionAllowed(true);
		basicDataSource.setUsername(user);
		basicDataSource.setPassword(pw);
		basicDataSource.setDriverClassName(className.getName());
		basicDataSource.setUrl(url);
		dataSource.setTargetDataSource(basicDataSource);

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager platformTransactionManager() {
		return new DataSourceTransactionManager(this.dataSource());
	}

	@Bean
	public DataSource dataSource() {
		return this.buildDataSource("jdbc:h2:tcp://localhost/~/activiti_example", org.h2.Driver.class, "sa", "");
	}

	@Bean
	public ELResolver elResolver() {
		return new SpringBeanFactoryELResolver();
	}

	@Bean
	public ProcessEngine processEngine() throws Throwable {
		ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
		processEngineFactoryBean.setDataSource(this.dataSource());
		processEngineFactoryBean.setTransactionManager(this.platformTransactionManager());
		processEngineFactoryBean.setElResolver(this.elResolver());
		processEngineFactoryBean.setDbSchemaStrategy(DbSchemaStrategy.DROP_CREATE);

		return processEngineFactoryBean.getObject();
	}

	@Bean
	public ProcessService processService() throws Throwable {
		return this.processEngine().getProcessService();
	}
}
