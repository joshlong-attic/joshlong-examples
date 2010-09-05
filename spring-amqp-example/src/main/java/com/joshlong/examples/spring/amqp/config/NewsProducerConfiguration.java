package com.joshlong.examples.spring.amqp.config;

import org.springframework.amqp.core.AmqpTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;


@Configuration
public class NewsProducerConfiguration extends NewsConfiguration {
    @Bean
    public BeanPostProcessor postProcessor() {
        return new ScheduledAnnotationBeanPostProcessor();
    }

    @Bean
    public NewsMaker newsMaker() {
        return new NewsMaker();
    }

    class NewsMaker {
        @Autowired
        private AmqpTemplate amqpTemplate;

        @Scheduled(fixedRate = 3000)
        public void produce() {
	        String news ="News at " + System.currentTimeMillis() ;
	        System.out.println( "news: "+ news) ;
            this.amqpTemplate.convertAndSend( news );
        }
    }
}
