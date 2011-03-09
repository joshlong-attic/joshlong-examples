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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MySqlCounter implements Counter {

	private JdbcTemplate jdbcTemplate;

	private String upsert = " INSERT INTO `counter` ( `name`, `val`) VALUES (?,?) ON DUPLICATE KEY UPDATE `val` = `val` + ? ";

	private String fetch = " SELECT `val` FROM `counter` WHERE `name` = ? ";

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public void decrement(String vName) {
		this.jdbcTemplate.update(upsert, vName, 0, -1);
	}

	@Override
	@Transactional
	public Integer getCount(String vName) {
		return this.jdbcTemplate.queryForInt(this.fetch, vName);
	}

	@Override
	@Transactional
	public void increment(String vName) {
		this.jdbcTemplate.update(upsert, vName, 1, 1);
	}
}
