/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joshlong.examples.activiti.gateway.example1;

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
        return simpleMessagingGateway.sendAndReceive(in);
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

        GatewayClient gatewayClient = classPathXmlApplicationContext.getBean(GatewayClient.class);

        gatewayClient.makeItSo("hello, world!");
    }

    public void setBeanName(String name) {
        this.name = name;
    }
}
