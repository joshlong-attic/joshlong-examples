package com.joshlong.hornetq.examples.consumers;

import javax.jms.TextMessage;

/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class MessageDrivenPOJOConsumer {

    public void announceReceiptOfMessage(TextMessage txtMsg) throws Throwable {
        System.out.println("Received the message: " + txtMsg.getText());
    }
}
