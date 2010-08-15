package com.joshlong.examples.activiti.gateway.activiti;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;


public class EchoServiceActivator {
    @ServiceActivator
    public Message<?> echo(Message<?> inMsg) throws Throwable {
        System.out.println(StringUtils.repeat("------------------------------", 50));
        System.out.println( "from Spring Integration: " + ToStringBuilder.reflectionToString(inMsg));
        System.out.println(StringUtils.repeat("------------------------------", 50));
        return inMsg ;
    }
}
