package com.joshlong.examples.activiti.spring.integration;

import org.activiti.impl.db.execution.DbExecutionImpl;
import org.activiti.pvm.ActivityBehavior;
import org.activiti.pvm.ActivityExecution;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * 
 *
 * @author Josh Long
 */
@Component
public class Announcer implements ActivityBehavior {

	@PostConstruct
	public void begin() throws Exception {
		System.out.println( "Starting "+ Announcer.class.getName());
	}

    public void execute(ActivityExecution activityExecution)
        throws Exception {
        System.out.println(  getClass().getName());

        DbExecutionImpl dbExecution = (DbExecutionImpl) activityExecution;

	    for (String k : dbExecution.getVariables().keySet())
            System.out.println(String.format("key = %s, value = %s", k,dbExecution.getVariable(k)));

        activityExecution.takeDefaultOutgoingTransition();
    }
}
