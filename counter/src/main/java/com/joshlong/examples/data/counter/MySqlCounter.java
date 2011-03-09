package com.joshlong.examples.data.counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MySqlCounter implements Counter {

	private JdbcTemplate jdbcTemplate;

	private String upsert = " INSERT INTO `counter` ( `name`, `val`) VALUES (?,?) ON DUPLICATE KEY UPDATE `val` = `val` + ? " ;

	private String fetch = " SELECT `val` FROM `counter` WHERE `name` = ? " ;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public void decrement(String vName) {
	 this.jdbcTemplate.update(upsert, vName, 0, -1) ;
	}

	@Override
	@Transactional
	public Integer getCount(String vName) {
		return this.jdbcTemplate.queryForInt(this.fetch,vName);
	}

	@Override
	@Transactional
	public void increment(String vName) {
		this.jdbcTemplate.update(upsert,vName ,1, 1) ;
	}

}
