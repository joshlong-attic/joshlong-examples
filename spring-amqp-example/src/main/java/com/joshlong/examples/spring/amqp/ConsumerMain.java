package com.joshlong.examples.spring.amqp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerMain {
	public static void main(String [] args) throws Exception {
		ClassPathXmlApplicationContext annotationConfigApplicationContext
				= new ClassPathXmlApplicationContext ( "/client.xml");
		annotationConfigApplicationContext.start();

	}
}
