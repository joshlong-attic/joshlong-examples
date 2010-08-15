package com.joshlong.examples.activiti.dummies;

import org.activiti.pvm.ActivityBehavior;
import org.activiti.pvm.ActivityExecution;


public class FooActivityBehavior implements ActivityBehavior {
    public void execute(ActivityExecution execution) throws Exception {
        System.out.println("Test!");
    }
}
