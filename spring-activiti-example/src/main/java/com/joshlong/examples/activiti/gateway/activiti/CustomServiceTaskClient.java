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
package com.joshlong.examples.activiti.gateway.activiti;

import org.activiti.Deployment;
import org.activiti.ProcessEngine;
import org.activiti.ProcessInstance;
import org.activiti.ProcessService;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * This class handles proving that we can forward an exection to Spring integration
 *
 * @author Josh Long
 */
public class CustomServiceTaskClient {
    public static void main(String[] args) throws Throwable {

        
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("d3.xml");

        ProcessEngine processEngine = classPathXmlApplicationContext.getBean(ProcessEngine.class);

        ProcessService processService = processEngine.getProcessService();
        Deployment deployment =processService.createDeployment().addClasspathResource("processes/sigateway1.bpmn20.xml").deploy();

        ProcessInstance processInstance = processService.startProcessInstanceByKey("sigatewayProcess");

        System.out.println("process started: " + processInstance.getId());
    }
}
