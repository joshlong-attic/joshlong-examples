package com.joshlong.examples.spring.amqp.config;

import com.joshlong.examples.spring.amqp.BroadcastAffiliate;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class NewsConsumerConfiguration extends NewsConfiguration {

    @Bean
    public BroadcastAffiliate broadcastAffiliate() {
        BroadcastAffiliate broadcastAffiliate = new BroadcastAffiliate();
        broadcastAffiliate.setAmqpTemplate(this.amqpTemplate());
        return broadcastAffiliate;
    }

    @Bean
    public SimpleMessageListenerContainer container() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(this.connectionFactory());
        simpleMessageListenerContainer.setConnectionFactory(this.connectionFactory());
        simpleMessageListenerContainer.setQueueName(this.newsQueue);
        simpleMessageListenerContainer.setMessageListener(new MessageListenerAdapter(this.broadcastAffiliate() ));
        return simpleMessageListenerContainer;
    }
}
