package com.joshlong.hornetq.examples.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import java.util.Enumeration;

/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class Browser {

    @Autowired
    private JmsTemplate jmsTemplate;

    private String destination;

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    @Scheduled(fixedDelay = 1000)
    public void browse() throws Throwable {

        int count = this.jmsTemplate.browse(this.destination, new BrowserCallback<Integer>() {
            public Integer doInJms(final Session session, final QueueBrowser browser) throws JMSException {
                Enumeration enumeration = browser.getEnumeration();
                int counter = 0;
                while (enumeration.hasMoreElements()) {
                    Message msg = (Message) enumeration.nextElement();
                    System.out.println(String.format("Found : %s", msg));
                    counter += 1;
                }
                return counter;
            }
        });

        System.out.println(String.format("There are %s messages", count));

    }
}
