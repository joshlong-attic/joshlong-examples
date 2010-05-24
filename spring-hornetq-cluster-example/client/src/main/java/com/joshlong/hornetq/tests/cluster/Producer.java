package com.joshlong.hornetq.tests.cluster;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;


/**
 * A simple producing bean
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
@Component
public class Producer implements ApplicationContextAware {
    private String destinationName = "ExampleQueue";
    private Executor executors = new SimpleAsyncTaskExecutor();
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext)
        throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void setup() throws Throwable {
        for (int i = 0; i < 2; i++) {
            send();
        }
    }

    public void send() throws Throwable {
        //assume that this is amortized in the cost of execution on a separate node 
        final SimplePojo[] pojos = new SimplePojo[500];

        for (int i = 0; i < pojos.length; i++) {
            pojos[i] = new SimplePojo();
        }

        final  long [] ids = new long[1000 * 1000];
        for(int i = 0 ; i < ids.length ; i++)
         ids[i] = i ;

        this.executors.execute(new Runnable() {
                @Override
                public void run() {
                    ConnectionFactory cf = applicationContext.getBean(ConnectionFactory.class);
                    JmsTemplate jmsTemplate = new JmsTemplate(cf);
                    jmsTemplate.setDefaultDestinationName(destinationName);

                    int total = 1000 * 200;
                    int counter = 0;

                    long start;
                    long stop;

                    String tName = Thread.currentThread().getName();
                    start = System.currentTimeMillis();
                    System.out.println("Starting " + tName + ".");

                    while (counter++ < total) {
                        jmsTemplate.send(new MessageCreator() {
                                @Override
                                public Message createMessage(final Session session)
                                    throws JMSException {
                                    return session.createObjectMessage( ids );
                                }
                            });
                    }

                    stop = System.currentTimeMillis();

                    System.out.println("Stopped " + tName + " at time: " + TimeUnit.MILLISECONDS.toSeconds((stop - start)));
                }
            });
    }
}
