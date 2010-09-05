package com.joshlong.examples.spring.amqp.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SingleConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class NewsConfiguration {
    protected String newsQueue = "news.queue";
    protected String host = "localhost";
    protected String user = "guest";
    protected String password = "guest";

    @Bean
    public Queue newsQueue() {
        return new Queue(this.newsQueue);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory(this.host);
        singleConnectionFactory.setUsername(this.user);
        singleConnectionFactory.setPassword(this.password);
        return singleConnectionFactory;
    }

    @Bean
    public RabbitTemplate amqpTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(this.newsQueue);
        template.setQueue(this.newsQueue);
        return template;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(this.amqpTemplate().getConnectionFactory());
    }
}
