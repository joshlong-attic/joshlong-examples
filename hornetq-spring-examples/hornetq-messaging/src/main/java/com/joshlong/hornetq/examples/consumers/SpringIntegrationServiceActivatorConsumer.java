package com.joshlong.hornetq.examples.consumers;

import org.springframework.integration.annotation.ServiceActivator;

import javax.jms.Message;

/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class SpringIntegrationServiceActivatorConsumer {
    @ServiceActivator
    public void processInboundJMSMessage(org.springframework.integration.core.Message<Message> msg) {
        System.out.println("Received message from Spring Integration: " + msg.toString());
    }
}
