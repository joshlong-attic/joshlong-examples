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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @author Josh Long
 */
public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	private static void exercise(String key, Counter counter) throws Throwable {
		counter.increment(key);
		counter.increment(key);
		logger.info("the count is now " + counter.getCount(key));

		counter.decrement(key);
		logger.info("the count is now " + counter.getCount(key));
	}

	public static void main(String[] args) throws Throwable {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:/counter.xml");
		Map<String, Counter> counters = ac.getBeansOfType(Counter.class);
		for (String beanName : counters.keySet())
			exercise(beanName, counters.get(beanName));
	}
}
