/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.joshlong.examples.data.counter;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.keyvalue.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.keyvalue.redis.core.RedisTemplate;
import org.springframework.data.keyvalue.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.keyvalue.redis.serializer.RedisSerializer;
import org.springframework.data.keyvalue.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.Assert;

import javax.sql.DataSource;

/**
 * configuration for my simple counter.
 *
 * @author jlong
 */
@Configuration
public class CounterConfiguration implements BeanFactoryAware {

	private ConversionService conversionService;

	private BeanFactory beanFactory;

	/// configuration
	@Value("${mysql.host}")
	private String mysqlHost = "127.0.0.1";

	@Value("${mysql.password}")
	private String mysqlPassword = "counter";

	@Value("${mysql.user}")
	private String mysqlUser = "counter";

	@Value("${redis.host}")
	private String redisHost = "127.0.0.1";

	@Value("${redis.password}")
	private String redisPassword;

	@Value("${redis.port}")
	private int redisPort = 6379;

	@Value("${redis.pooling}")
	private boolean pooling = false;

	@Value("${redis.timeout}")
	private int timeout = 2000;

	/// redis
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(this.redisHost);
//		jedisConnectionFactory.setPassword(this.redisPassword);
		jedisConnectionFactory.setUsePool(this.pooling);
		jedisConnectionFactory.setPort(this.redisPort);
		jedisConnectionFactory.setTimeout(this.timeout);
		return jedisConnectionFactory;
	}

	@Bean
	public RedisSerializer serializer() {
		StringRedisSerializer redisSerializer = new StringRedisSerializer();
		return redisSerializer;
	}

	@Bean
	public RedisTemplate<String, Integer> redisTemplate() {
		RedisTemplate<String, Integer> rt = new RedisTemplate<String, Integer>();
		rt.setConnectionFactory(this.jedisConnectionFactory());
		rt.setDefaultSerializer(serializer());
		return rt;
	}

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

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
		Assert.isInstanceOf(ConfigurableBeanFactory.class, beanFactory, "the context used must be an instance of " + ConfigurableBeanFactory.class.getName());
		ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
	}
}
