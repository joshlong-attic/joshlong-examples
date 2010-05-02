package com.joshlong.hornetq.examples.consumers;

import javax.jms.TextMessage;

/**
 * A simple MDP (Message-Driven POJOs, as opposed to Message Driven Beans from JEE). The MDP is configured using Spring and the jms: namespace support.
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class MessageDrivenPOJOConsumer {

    public void announceReceiptOfMessage(TextMessage txtMsg) throws Throwable {
        System.out.println("Received the message: " + txtMsg.getText());
    }
}
