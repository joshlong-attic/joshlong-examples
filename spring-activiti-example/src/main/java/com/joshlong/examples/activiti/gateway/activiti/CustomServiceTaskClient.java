package com.joshlong.examples.activiti.gateway.activiti;

import org.activiti.ProcessEngine;
import org.activiti.ProcessInstance;
import org.activiti.ProcessService;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * This class handles proving that we can forward an exection to Spring integration
 *
 * @author Josh Long
 */
public class CustomServiceTaskClient {
    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("d3.xml");

        ProcessEngine processEngine = classPathXmlApplicationContext.getBean(ProcessEngine.class);

        ProcessService processService = processEngine.getProcessService();
        processService.createDeployment().addClasspathResource("processes/sigateway1.bpmn20.xml").deploy();

        ProcessInstance processInstance = processService.startProcessInstanceByKey("javaServiceDelegation");

        System.out.println("process started: " + processInstance.getId());
    }
}
