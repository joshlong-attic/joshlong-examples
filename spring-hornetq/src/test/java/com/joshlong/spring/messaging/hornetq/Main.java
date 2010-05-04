package com.joshlong.spring.messaging.hornetq;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.ConnectionFactory;

/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class Main {
    public static void main (String [] args) throws Throwable {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("hornetq/a.xml");
        classPathXmlApplicationContext.start();

        ConnectionFactory hornetQConnectionFactory = classPathXmlApplicationContext.getBean("connectionFactory",ConnectionFactory.class);
        System.out.println(ToStringBuilder.reflectionToString(hornetQConnectionFactory ));

    }
}
