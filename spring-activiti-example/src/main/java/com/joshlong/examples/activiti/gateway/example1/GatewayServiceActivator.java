package com.joshlong.examples.activiti.gateway.example1;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.integration.annotation.ServiceActivator;

public class GatewayServiceActivator {

    @ServiceActivator
    public Object handleMessage(Object request) throws Throwable {
        System.out.println("Received following object on requestChannel: " + ToStringBuilder.reflectionToString(request));
        if(request instanceof String){
            return ((String) request).toUpperCase();
        }
        return request;
    }
}
