package com.joshlong.examples.activiti;

import org.activiti.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ActivitiClient {

    @Autowired  private ProcessEngine processEngine ;

    @PostConstruct
    public void start () throws Throwable {

    }

    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext classPathXmlApplicationContext
                 = new ClassPathXmlApplicationContext("d1.xml");
        classPathXmlApplicationContext.start();
    }
}