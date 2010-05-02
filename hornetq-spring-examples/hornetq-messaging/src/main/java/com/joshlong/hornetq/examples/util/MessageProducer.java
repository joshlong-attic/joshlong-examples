package com.joshlong.hornetq.examples.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * This class just generates messages that contain unique payloads every 2 seconds. The idea is that a constant stream of inbound messages will make more apparent the magic of the various consumers.
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class MessageProducer {

    private String destination;

    @Autowired
    private Browser browser;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Required
    public void setDestination(final String destination) {
        this.destination = destination;
    }

    @Scheduled(fixedDelay = 2000)
    public void kickoff() throws Throwable {
        send("text " + System.currentTimeMillis());
    }

    private void send(final String hash) throws Throwable {

        this.jmsTemplate.send(this.destination, new MessageCreator() {
            public Message createMessage(final Session session) throws JMSException {

                TextMessage textMessage = session.createTextMessage(hash);
                textMessage.setStringProperty("_HQ_LVQ_NAME", hash); // this is why it gets trapped
                return textMessage;
            }
        });
        browser.browse();
        System.out.println(String.format("\tSending message with hash: %s ", hash));
    }

}
