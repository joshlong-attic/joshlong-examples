package com.joshlong.examples.activiti.spring;

import org.activiti.ProcessEngine;
import org.activiti.ProcessInstance;
import org.activiti.ProcessService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.processing.ProcessingEnvironment;


/**
 * This class handles proving that we can forward an exection to Spring integration
 *
 * @author Josh Long
 */
 
public class CustomServiceTaskClient {
    @Autowired
    private ProcessEngine processEngine;

    @PostConstruct
    public void begin() throws Throwable {
        ProcessService processService = processEngine.getProcessService();
        processService.createDeployment().addClasspathResource("processes/sigateway1.bpmn20.xml").deploy();

        ProcessInstance processingEnvironment =
                processService.startProcessInstanceByKey("javaServiceDelegation");


        System.out.println( "process started!");
        


    }

    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("d3.xml");
    }
}
