package com.joshlong.examples;

import org.activiti.impl.el.StaticElResolver;
import org.activiti.pvm.ActivityBehavior;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * This simply provides access to every {@link org.activiti.pvm.ActivityBehavior} in the bean factory
 *
 * @author Josh Long
 */
public class SpringBeanFactoryELResolver extends StaticElResolver implements ApplicationContextAware, InitializingBean {
    private ApplicationContext applicationContext;
    private Map<String, Object> map = new ConcurrentHashMap<String, Object>();

    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        for (String beanName : this.applicationContext.getBeanNamesForType(ActivityBehavior.class))
            this.map.put(beanName, this.applicationContext.getBean(beanName));

        this.setMap(this.map);
    }
}
