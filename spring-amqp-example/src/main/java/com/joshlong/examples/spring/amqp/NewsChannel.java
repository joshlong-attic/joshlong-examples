package com.joshlong.examples.spring.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class NewsChannel {

	@Autowired
	private AmqpTemplate amqpTemplate ;

	public void broadcast(Date date, String title, String news) throws Exception {
		
	}


}
