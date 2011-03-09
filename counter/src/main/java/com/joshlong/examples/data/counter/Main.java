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
