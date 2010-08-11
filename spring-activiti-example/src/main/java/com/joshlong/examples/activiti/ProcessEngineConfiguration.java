package com.joshlong.examples.activiti;

import org.activiti.IdentityService;
import org.activiti.ProcessEngine;
import org.activiti.ProcessService;
import org.activiti.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessEngineConfiguration {
    @Autowired
    private ProcessEngine processEngine;

    @Bean
    public IdentityService identityService() {
        return this.processEngine.getIdentityService();
    }

    @Bean
    public ProcessService processService() {
        return this.processEngine.getProcessService();
    }


    @Bean
    public TaskService taskService() {
        return this.processEngine.getTaskService();
    }
}
