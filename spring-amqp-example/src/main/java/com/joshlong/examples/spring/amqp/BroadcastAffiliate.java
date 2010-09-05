package com.joshlong.examples.spring.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class BroadcastAffiliate {
    private AmqpTemplate amqpTemplate;

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @PostConstruct
    public void establishListener() throws Exception {
        System.out.println("establishListener");
    }

    /**
     * Gets called when a {@link NewsChannel} publishes news that needs to be received and re-broadcast appropriately.
     *
     * @param newsStory the news story
     * @throws Exception escape-hatch {@link com.joshlong.examples.spring.amqp.NewsStory}
     */
    public void breakForNews(NewsStory newsStory) throws Exception {
        System.out.println("this just in...  " + newsStory);
    }
}
