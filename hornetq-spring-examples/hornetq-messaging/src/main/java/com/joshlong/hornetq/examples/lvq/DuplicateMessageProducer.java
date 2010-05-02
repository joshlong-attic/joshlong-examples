package com.joshlong.hornetq.examples.lvq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.PostConstruct;
import javax.jms.*;

/**
 * This class will wake up on context start, run through a series of messages with the same key, and then send an alternative. We should be able to witness only 2 (unique) messages in the queue.
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class DuplicateMessageProducer implements MessageListener {

    @Autowired
    private volatile JmsTemplate jmsTemplate;

    private String destination;

    public void setDestination(final String dn) {
        this.destination = dn;
    }

    @PostConstruct
    public void start() throws Throwable {
        for (int i = 0; i < 10; i++) {
            send("2322322aa422");
        }
        send("-0604340");
    }

    private void send(final String hash) throws Throwable {
        this.jmsTemplate.send(this.destination, new MessageCreator() {
            public Message createMessage(final Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(hash);
                textMessage.setStringProperty("_HQ_LVQ_NAME", hash); // this is why it gets trapped
                return textMessage;
            }
        });
        System.out.println(String.format("Sending message with hash: %s ", hash));
    }

    public void onMessage(final Message message) {
        TextMessage textMessage = (TextMessage) message;
        System.out.println("Received message " + textMessage.toString());
    }

    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("examples-lvq.xml");
        ctx.start();
    }
}
