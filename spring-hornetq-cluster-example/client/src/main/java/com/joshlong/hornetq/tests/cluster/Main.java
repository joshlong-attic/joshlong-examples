package com.joshlong.hornetq.tests.cluster;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class Main {
    public static void main(String [] args) throws Throwable {

        ClassPathXmlApplicationContext classPathXmlApplicationContext
                = new ClassPathXmlApplicationContext("hornetqns-example.xml");
        
    }
}
