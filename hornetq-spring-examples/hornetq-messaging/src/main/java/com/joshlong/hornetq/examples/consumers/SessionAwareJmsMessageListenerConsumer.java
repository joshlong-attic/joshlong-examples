package com.joshlong.hornetq.examples.consumers;

import com.joshlong.hornetq.examples.util.Browser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Creates a message listener container in the same way our {@link javax.jms.MessageListener} implementation worked, except this one will provide the user a callback to the {@link javax.jms.Session}, plus, this one is generic friendly. The argument to #onMessage is parameterized by type TODO why
 * don't the browsers see the message counts?
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class SessionAwareJmsMessageListenerConsumer implements SessionAwareMessageListener<TextMessage> {

    @Autowired
    private Browser browser;

    public void onMessage(final TextMessage message, final Session session) throws JMSException {

        browser.browse();

        System.out.println("Received message: " + message.getText());
    }

    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("consumers/session-aware-mdp.xml");
        classPathXmlApplicationContext.start();
    }
}
