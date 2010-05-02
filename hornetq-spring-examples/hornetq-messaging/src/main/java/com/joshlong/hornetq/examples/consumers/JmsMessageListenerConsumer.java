package com.joshlong.hornetq.examples.consumers;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Manually sets up a MessageListener and uses a MessageListenerContainerAdapte
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class JmsMessageListenerConsumer implements MessageListener {

    public void onMessage(final Message msg) {
        System.out.println("Received " + msg.toString());
    }

    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("consumers/standard-mdp.xml");
        classPathXmlApplicationContext.start();
    }
}
