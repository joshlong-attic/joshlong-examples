package com.joshlong.examples.activiti;

import org.activiti.*;
import org.activiti.identity.Group;
import org.activiti.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class ExpenseReportClient {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ProcessService processService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;

    private String myUser = "joshlong";
    private User user;
    private Group accounting, mgmt;

    @PostConstruct
    public void setup() throws Throwable {

        processService.createDeployment()
                .addClasspathResource("processes/expensereport.bpmn20.xml")
                .deploy();

        this.user = this.identityService.newUser("joshlong");
        this.identityService.saveUser( this.user );

        this.accounting = this.identityService.newGroup("accouting");
        this.mgmt = this.identityService.newGroup("management");


        for (Group g : Arrays.asList(this.accounting, this.mgmt))
            this.identityService.saveGroup(g);         




        ProcessInstance processInstance =
                processService.startProcessInstanceByKey("financialReport");


        List<Task> tasks;

        tasks = taskService.createTaskQuery().candidateGroup("accounting").list();

        for (Task t : tasks) {
            String taskId = t.getId();
            taskService.claim(taskId, myUser);
            taskService.complete(taskId);
        }

        tasks = taskService.createTaskQuery().candidateGroup("management").list();

        for (Task t : tasks) {
            String taskId = t.getId();
            taskService.claim(taskId, myUser);
            taskService.complete(taskId);
        }


    }

    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("d1.xml");

    }

}
