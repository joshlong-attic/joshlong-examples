package com.joshlong.examples.activiti.spring.integration;

import org.activiti.pvm.ActivityExecution;
import org.apache.commons.lang.StringUtils;
import org.springframework.integration.Message;
import org.springframework.integration.activiti.ActivitiConstants;
import org.springframework.integration.annotation.Headers;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageBuilder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Map;


/**
 * Demonstrates using Spring Integration to say "Hello!"
 *
 * @author Josh Long
 */
@Component
public class SpringIntegrationGatewayServiceActivator {

    @PostConstruct
    public void start() throws Throwable {
        System.out.println(SpringIntegrationGatewayServiceActivator.class.getName() + " starting...");
    }

    @ServiceActivator
    public Message<ActivityExecution> receiveWorkFromActivitiProcess(
		    @Headers Map<String, Object> headers ,
		    @Payload ActivityExecution activityExecution) {



	    System.out.println(StringUtils.repeat( "-"  ,100));
	    System.out.println(  SpringIntegrationGatewayServiceActivator.class.getName());
        for (String k : headers.keySet())
            System.out.println(String.format("key = %s, value = %s", k, headers.get(k)));
	    System.out.println(StringUtils.repeat( "-"  ,100));

	    return MessageBuilder.withPayload(activityExecution).
			    setHeader(ActivitiConstants.WELL_KNOWN_SPRING_INTEGRATION_HEADER_PREFIX +"userId" , 22).
			    copyHeadersIfAbsent(headers).build() ;


    }
}
