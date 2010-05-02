package com.joshlong.hornetq.examples.consumers;

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
}
