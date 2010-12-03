package org.springsource.examples.eventdrivenweb.atmosphere.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.comet.AsyncHttpRequestHandlingMessageAdapter;

import javax.annotation.PostConstruct;


/**
 * Sets up the beans specific to the Atmosphere-specific configuration.
 *
 * @author Josh Long
 * @since 1.0
 */
@Configuration
public class AtmosphereConfiguration extends WebConfiguration implements BeanFactoryAware, BeanNameAware {
    @Value("#{out}")
    protected MessageChannel messageChannel;
    protected String beanName;
    protected BeanFactory beanFactory;

    @PostConstruct
    public void start() {
        System.out.println(AtmosphereConfiguration.class.getName() + " is starting...");
    }

    @Bean(name = "/ticker")
    public AsyncHttpRequestHandlingMessageAdapter asyncHttpRequestHandlingMessageAdapter() {
        AsyncHttpRequestHandlingMessageAdapter cometEndpoint = new AsyncHttpRequestHandlingMessageAdapter();
        cometEndpoint.setMessageChannel(this.messageChannel);

        return cometEndpoint;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory)
        throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
