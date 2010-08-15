package com.joshlong.examples.activiti.dummies;

import org.activiti.ProcessEngine;
import org.activiti.ProcessService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class HelloWorldClient {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ProcessService processService;

    @PostConstruct
    public void start() throws Throwable {
        this.processService.createDeployment().addClasspathResource("processes/helloworld.bpmn20.xml").deploy();
        processService.startProcessInstanceByKey("helloWorld");
    }

    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("d1.xml");
        classPathXmlApplicationContext.start();
    }
}
