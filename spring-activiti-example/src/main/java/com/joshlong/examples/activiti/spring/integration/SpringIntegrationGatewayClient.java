package com.joshlong.examples.activiti.spring.integration;

import org.activiti.ProcessInstance;
import org.activiti.ProcessService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;


@Component
public class SpringIntegrationGatewayClient {

	@Autowired
    private ProcessService processService;
    
    public ProcessInstance launchCustomerFulfillmentProcess(long customerId)  {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("customerId", customerId);
        return processService.startProcessInstanceByKey("sigatewayProcess", variables);
    }

    @PostConstruct
    public void start() throws Throwable {
        processService.createDeployment().addClasspathResource(
		        "processes/integration/spring-integration-request-reply-from-activiti-with-stalled-waitstate-at-end.bpmn20.xml").deploy();
    }

    public static void main(String[] args) throws Throwable {
        ApplicationContext ac = new ClassPathXmlApplicationContext("activiti-si-gateway1.xml");

        SpringIntegrationGatewayClient client = ac.getBean(SpringIntegrationGatewayClient.class);

	    client.launchCustomerFulfillmentProcess(1);
	    
    }
}
