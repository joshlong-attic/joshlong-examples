package com.joshlong.examples.data.counter;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * configuration for my simple counter.
 *
 * @author jlong
 */
@Configuration
public class CounterConfiguration {

	/// configuration
	@Value("${mysql.host}")
	private String mysqlHost;

	@Value("${mysql.password}")
	private String mysqlPassword;

	@Value("${mysql.user}")
	private String mysqlUser;

	/// redis

	/// mongodb

	/// mysql
	@Bean
	public DataSource dataSource() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setPassword(this.mysqlPassword);
		ds.setUser(this.mysqlUser);
		ds.setUrl(this.buildMysqlUrl(this.mysqlHost));
		return ds;
	}

	@Bean
	public PlatformTransactionManager dataSourceTransactionManager() {
		return new DataSourceTransactionManager(this.dataSource());
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(this.dataSource());
	}

	private String buildMysqlUrl(String host) {
		return "jdbc:mysql://" + host + ":3306/counter";
	}
}
