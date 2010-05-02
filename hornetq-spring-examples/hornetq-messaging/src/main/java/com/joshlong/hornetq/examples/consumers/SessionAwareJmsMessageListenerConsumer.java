package com.joshlong.hornetq.examples.consumers;

import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Creates a message listener container in the same way our {@link javax.jms.MessageListener} implementation worked, except this one will provide the user a callback to the {@link javax.jms.Session}, plus, this one is generic friendly. The argument to #onMessage is parameterized by type
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class SessionAwareJmsMessageListenerConsumer implements SessionAwareMessageListener<TextMessage> {
    public void onMessage(final TextMessage message, final Session session) throws JMSException {
        System.out.println("Received message: " + message.getText());
    }
}
