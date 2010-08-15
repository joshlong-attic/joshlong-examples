package com.joshlong.examples.activiti.gateway.activiti;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;


public class EchoServiceActivator {
    @ServiceActivator
    public Object echho(Object in) throws Throwable {
        System.out.println(StringUtils.repeat("------------------------------", 100));
        System.out.println(ToStringBuilder.reflectionToString(in));

        return in;
    }
}
