package com.joshlong.examples.activiti.gateway.activiti;

import org.activiti.ProcessEngine;

import org.activiti.impl.ProcessEngineImpl;
import org.activiti.impl.cmd.SendEventCmd;
import org.activiti.impl.db.execution.DbExecutionImpl;
import org.activiti.impl.interceptor.CommandExecutor;

import org.activiti.pvm.ActivityBehavior;
import org.activiti.pvm.ActivityExecution;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.core.MessageBuilder;
import org.springframework.integration.gateway.SimpleMessagingGateway;

import org.springframework.util.Assert;

import java.util.Map;
import java.util.logging.Logger;


/**
 * This class is plugged into an Activit workflow. <serviceTask /> let's us plugin a custom {@link org.activiti.pvm.ActivityBehavior}.
 * <p/>
 * We need to build an {@link org.activiti.pvm.ActivityBehavior} that can send and receive the message, propagating the {@code executionId} and potentially process variables/ header variables.
 * <p/>
 * Known Limitations:
 * <p/>
 * <OL>
 * <LI>The Activiti PVM can't be killed in this example - the wait-state must exist for the Spring Integration gateway to retreive the message.</LI>
 * <LI>The Spring Integration gateway in of itself is in-VM only as well, however this is not to say that you couldn't forward the message through a
 * JMS gateway or something to have processing leave the bounds of the VM then return via the reply channel configured on this gateway to ultimately be resolved,
 * Correllation would need to occur to ensure the reply is correctly redelivered.
 * </LI>
 * </OL>
 *
 * @author Josh Long (based on an idea sketched out w/ Dave Syer and Tom Baeyans)
 * @see org.activiti.impl.bpmn.ReceiveTaskActivity this the ActivityBehavior impl that ships w/ Activiti that has the machinery to wake up when signal'd.
 */
public class ActivityBehaviorMessagingGateway extends SimpleMessagingGateway implements ActivityBehavior  {
    public static final String WELL_KNOWN_EXECUTION_ID_HEADER_KEY = "activiti_spring_integration_executionId";
    private ProcessEngine processEngine;
    //private CommandExecutor commandExecutor;

    public void setProcessEngine(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    private boolean updateProcessVariablesFromResponseMessageHeaders;
    private boolean forwardProcessVariablesAsMessageHeaders;

    public void setForwardProcessVariablesAsMessageHeaders(boolean forwardProcessVariablesAsMessageHeaders) {
        this.forwardProcessVariablesAsMessageHeaders = forwardProcessVariablesAsMessageHeaders;
    }


    /*  private void receiveFromSpringIntegration(Message<?> msgFromSI) {
        String executionId = (String) msgFromSI.getHeaders().get(WELL_KNOWN_EXECUTION_ID_HEADER_KEY);
        this.commandExecutor.execute(new SendEventCmd(executionId, null));
    }*/
    @Override
    protected void onInit() throws Exception {
   /*     if (processEngine instanceof ProcessEngineImpl) {
            this.commandExecutor = ((ProcessEngineImpl) processEngine).getProcessEngineConfiguration().getCommandExecutor();
        }*/
    }

    public void setUpdateProcessVariablesFromResponseMessageHeaders(boolean updateProcessVariablesFromResponseMessageHeaders) {
        this.updateProcessVariablesFromResponseMessageHeaders = updateProcessVariablesFromResponseMessageHeaders;
    }

    public void execute(ActivityExecution execution) throws Exception {
        if (this.logger.isDebugEnabled()) {
            logger.debug("Starting execute(ActivityExecution) " + ToStringBuilder.reflectionToString(execution));
        }

        String executionId = null;

        if (execution instanceof DbExecutionImpl) {
            executionId = ((DbExecutionImpl) execution).getId();
        }

        Assert.state(!StringUtils.isEmpty(executionId), "the 'executionId' can't be null!"); // though, technically, it can since we're not necessarily using it to revive any records asynchronously

        MessageBuilder<?> messageBuilder = MessageBuilder.withPayload(execution).setHeader(WELL_KNOWN_EXECUTION_ID_HEADER_KEY, executionId).setCorrelationId(executionId);

        if (this.forwardProcessVariablesAsMessageHeaders) {
            Map<String, Object> variables = processEngine.getProcessService().getVariables(executionId);

            if ((variables != null) && (variables.size() > 0)) {
                messageBuilder = messageBuilder.copyHeadersIfAbsent(variables);
            }
        }

        Message<?> msg = messageBuilder.build();

        Message<?> responseFromSpringIntegration = this.sendAndReceiveMessage(msg);

        if (this.updateProcessVariablesFromResponseMessageHeaders) {
            MessageHeaders messageHeaders = responseFromSpringIntegration.getHeaders();

            for (String k : messageHeaders.keySet())
                this.processEngine.getProcessService().setVariable(executionId, k, messageHeaders.get(k));
        }

        if (this.logger.isDebugEnabled()) {
            logger.debug("Execiting execute(ActivityExecution) " + ToStringBuilder.reflectionToString(execution));
        }
    }
}
