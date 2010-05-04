package com.joshlong.spring.messaging.hornetq;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
@Component
public class Main {
    @Autowired
    private JmsTemplate jmsTemplate;

    @PostConstruct
    public void start() {
        final String destination = "dupesQueue";

        // send
        jmsTemplate.send(destination,
            new MessageCreator() {
                public Message createMessage(final Session session)
                    throws JMSException {
                    return session.createTextMessage(String.format("Hello, world! @ %s", System.currentTimeMillis() + ""));
                }
            });

        // receive
        Message msg = jmsTemplate.receive(destination);
        System.out.println(ToStringBuilder.reflectionToString(msg));
    }

    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("hornetq/a.xml");
        classPathXmlApplicationContext.start();
    }
}
