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

import org.activiti.pvm.ActivityBehavior;

import java.util.HashMap;
import java.util.Map;


/**
 * This simply provides access to every {@link org.activiti.pvm.ActivityBehavior} in the {@link org.springframework.beans.factory.BeanFactory} it's configured in
 *
 * @author Josh Long
 */
public class ActivityBehaviorSpringBeanFactoryELResolver extends SpringBeanFactoryELResolver {
    public void afterPropertiesSet() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        for (String beanName : this.applicationContext.getBeanNamesForType(ActivityBehavior.class))
            map.put(beanName, this.applicationContext.getBean(beanName));

        this.setMap(map);
    }
}
