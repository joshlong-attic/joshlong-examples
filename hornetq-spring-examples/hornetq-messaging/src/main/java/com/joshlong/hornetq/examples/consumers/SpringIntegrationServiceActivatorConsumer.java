package com.joshlong.hornetq.examples.consumers;

import org.springframework.integration.annotation.ServiceActivator;

import javax.jms.Message;

/**
 * This consumer leverages the Spring Integration service bus to "adapt" our code to the external middleware which - in this case - is HornetQ. By the time we receive it, the JMS message ({@link javax.jms.Message}) is the payload to an envelope {@link org.springframework.integration.core.Message}
 * type that's native to the Spring Integration framework.
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class SpringIntegrationServiceActivatorConsumer {
    @ServiceActivator
    public void processInboundJMSMessage(org.springframework.integration.core.Message<Message> msg) {
        System.out.println("Received message from Spring Integration: " + msg.toString());
    }
}
