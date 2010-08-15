package com.joshlong.examples.activiti.gateway.activiti;

import org.activiti.ProcessEngine;

import org.activiti.impl.ProcessEngineImpl;
import org.activiti.impl.cmd.SendEventCmd;
import org.activiti.impl.db.execution.DbExecutionImpl;
import org.activiti.impl.interceptor.CommandExecutor;

import org.activiti.pvm.ActivityBehavior;
import org.activiti.pvm.ActivityExecution;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.springframework.integration.Message;
import org.springframework.integration.core.MessageBuilder;
import org.springframework.integration.gateway.SimpleMessagingGateway;


/**
 * This class is plugged into an Activit workflow. <serviceTask /> let's us plugin a custom {@link org.activiti.pvm.ActivityBehavior}.
 * <p/>
 * We need to build an {@link org.activiti.pvm.ActivityBehavior} that can send and receive the message, propagating the {@code executionId} and potentially process variables/ header variables.
 *
 * @author Josh Long (based on an idea sketched out w/ Dave Syer)
 * @see org.activiti.impl.bpmn.ReceiveTaskActivity this the ActivityBehavior impl that ships w/ Activiti that has the machinery to wake up when signal'd.
 */
public class SpringIntegrationActivityBehaviorMessagingGateway extends SimpleMessagingGateway implements ActivityBehavior, InitializingBean, ApplicationContextAware {
    public static final String WELL_KNOWN_EXECUTION_ID_HEADER_KEY = "executionId";
    private ApplicationContext applicationContext;
    private ProcessEngine processEngine;
    private CommandExecutor commandExecutor;

    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void receiveFromSpringIntegration(Message<?> msgFromSI) {
        String executionId = (String) msgFromSI.getHeaders().get(WELL_KNOWN_EXECUTION_ID_HEADER_KEY);
        this.commandExecutor.execute(new SendEventCmd(executionId, null));
    }

    @Override
    protected void doStart() {
        this.processEngine = applicationContext.getBean(ProcessEngine.class);

        if (processEngine instanceof ProcessEngineImpl) {
            this.commandExecutor = ((ProcessEngineImpl) processEngine).getProcessEngineConfiguration().getCommandExecutor();
        }

        super.doStart();
    }

    public void execute(ActivityExecution execution) throws Exception {
        System.out.println("execute(ActivityExecution): start");

        String executionId = null;

        if (execution instanceof DbExecutionImpl) {
            executionId = ((DbExecutionImpl) execution).getId();
        }

        Message<String> msg = MessageBuilder.withPayload(executionId).setHeader(WELL_KNOWN_EXECUTION_ID_HEADER_KEY, executionId).setCorrelationId(executionId).build();
        receiveFromSpringIntegration(this.sendAndReceiveMessage(msg));
        System.out.println("execute(ActivityExecution): stop");
    }
}
