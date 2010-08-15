package com.joshlong.examples.activiti.gateway.example1;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.gateway.SimpleMessagingGateway;

import javax.annotation.PostConstruct;


/**
 * This is a simple client that demonstrates a working Gateway
 */
public class GatewayClient implements BeanNameAware, BeanFactoryAware {
    private BeanFactory beanFactory;
    private SimpleMessagingGateway simpleMessagingGateway = new SimpleMessagingGateway();
    @Value("#{requestChannel}")
    private MessageChannel requestChannel;
    @Value("#{replyChannel}")
    private MessageChannel replyChannel;
    private String name;

    public void setBeanFactory(BeanFactory beanFactory)
        throws BeansException {
        this.beanFactory = beanFactory;
    }

    public Object makeItSo(Object in) throws Throwable {
        Object response = simpleMessagingGateway.sendAndReceive( in );
        System.out.println("The response is " + ToStringBuilder.reflectionToString(response));
        return response ;
    }

    @PostConstruct
    public void begin() throws Throwable {
        simpleMessagingGateway.setRequestChannel(this.requestChannel);
        simpleMessagingGateway.setReplyChannel(this.replyChannel);
        simpleMessagingGateway.setAutoStartup(false);
        simpleMessagingGateway.setBeanFactory(this.beanFactory);
        simpleMessagingGateway.setBeanName(this.name);
        simpleMessagingGateway.afterPropertiesSet();
        simpleMessagingGateway.start();
    }

    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("d4.xml");

        GatewayClient gatewayClient
                  = classPathXmlApplicationContext.getBean(GatewayClient.class);

        gatewayClient.makeItSo( "hello, world!") ;
    }

    public void setBeanName(String name) {
        this.name = name;
    }
}
