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
package com.joshlong.examples.activiti;

import org.activiti.impl.el.StaticElResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class SpringBeanFactoryELResolver extends StaticElResolver implements ApplicationContextAware, InitializingBean {

    protected ApplicationContext applicationContext;

    private Map<String, Object> map = new ConcurrentHashMap<String, Object>();

    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        for (String beanName : this.applicationContext.getBeanDefinitionNames() )
            this.map.put(beanName, this.applicationContext.getBean(beanName));

        this.setMap(this.map);
    }
}
