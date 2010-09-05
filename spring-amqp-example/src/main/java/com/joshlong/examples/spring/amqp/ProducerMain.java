package com.joshlong.examples.spring.amqp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProducerMain {
	public static void main(String [] args) throws Exception {
		ClassPathXmlApplicationContext classPathXmlApplicationContext  = 
				new ClassPathXmlApplicationContext("/producer.xml");
		classPathXmlApplicationContext.start() ;
	}
}
