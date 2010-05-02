package com.joshlong.hornetq.examples.consumers;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * A simple MDP (Message-Driven POJOs, as opposed to Message Driven Beans from JEE). The MDP is configured using Spring and the jms: namespace support.
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class MessageDrivenPOJOConsumer {

    public void handleMessage(String message) throws Throwable {
        System.out.println("Received the message: " + message.toString());
    }

    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("consumers/jms-mlc-adapter-mdp.xml");
        classPathXmlApplicationContext.start();

    }
}
