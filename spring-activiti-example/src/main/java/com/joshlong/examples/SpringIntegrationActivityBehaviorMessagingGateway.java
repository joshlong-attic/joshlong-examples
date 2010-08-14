package com.joshlong.examples;

import org.activiti.ProcessEngine;
import org.activiti.impl.ProcessEngineImpl;
import org.activiti.impl.cmd.SendEventCmd;
import org.activiti.impl.interceptor.CommandExecutor;
import org.activiti.pvm.ActivityBehavior;
import org.activiti.pvm.ActivityExecution;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.integration.Message;
import org.springframework.integration.core.MessageBuilder;
import org.springframework.integration.gateway.AbstractMessagingGateway;
import org.springframework.integration.gateway.SimpleMessagingGateway;
import org.springframework.stereotype.Component;


/**
 * This class is plugged into an Activit workflow. <serviceTask /> let's us plugin a custom {@link org.activiti.pvm.ActivityBehavior}.
 *
 * We need to build an {@link org.activiti.pvm.ActivityBehavior} that can send and receive the message, propagating the {@code executionId} and potentially process variables/ header variables.
 *
 * @author Josh Long (based on an idea sketched out w/ Dave Syer)
 *
 * @see org.activiti.impl.bpmn.ReceiveTaskActivity this the ActivityBehavior impl that ships w/ Activiti that has the machinery to wake up when signal'd.
 */
@Component
public class SpringIntegrationActivityBehaviorMessagingGateway extends SimpleMessagingGateway implements ActivityBehavior, InitializingBean {
    public static final String WELL_KNOWN_EXECUTION_ID_HEADER_KEY = "executionId";
    private ProcessEngine processEngine;
    private CommandExecutor commandExecutor;

    @Required
    public void setProcessEngine(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    private void receiveFromSpringIntegration(Message<?> msgFromSI) {
        String executionId = (String) msgFromSI.getHeaders().get(WELL_KNOWN_EXECUTION_ID_HEADER_KEY);
        this.commandExecutor.execute(new SendEventCmd(executionId, null));
    }

    public void execute(ActivityExecution execution) throws Exception {
        String executionId = execution.getActivity().getId();
        Message<String> msg = MessageBuilder.withPayload(executionId).setCorrelationId(executionId).build();
        receiveFromSpringIntegration(this.sendAndReceiveMessage(msg));
    }

    @Override
    protected void doStart() {
        if (processEngine instanceof ProcessEngineImpl) {
            this.commandExecutor = ((ProcessEngineImpl) processEngine).getProcessEngineConfiguration().getCommandExecutor();
        }

        super.doStart();
    }
}
