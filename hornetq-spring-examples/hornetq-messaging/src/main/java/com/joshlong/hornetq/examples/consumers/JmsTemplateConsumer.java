package com.joshlong.hornetq.examples.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.PostConstruct;
import javax.jms.Message;

/**
 * Receives messages using JMS the manual way
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class JmsTemplateConsumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    private String destination;

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    @PostConstruct
    public void start() throws Throwable {
        Message msg;
        while ((msg = jmsTemplate.receive(this.destination)) != null) {
            System.out.println("Received " + msg.toString());
        }
    }

}
