package com.joshlong.examples.activiti.spring;

import org.activiti.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
 
/**
 *
 * This class handles proving that we can forward an exection to Spring integration
 *
 *
 * @author Josh Long
 */
@Component
public class CustomServiceTaskClient {
    @PostConstruct
    public void begin() throws Throwable {

    }

    @Autowired private ProcessEngine processEngine ;
       public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext classPathXmlApplicationContext
                = new ClassPathXmlApplicationContext("d2.xml");
        classPathXmlApplicationContext.start();
    }
}
