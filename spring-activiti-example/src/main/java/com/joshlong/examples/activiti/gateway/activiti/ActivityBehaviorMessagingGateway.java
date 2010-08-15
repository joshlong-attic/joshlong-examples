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

import org.activiti.ProcessEngine;
import org.activiti.impl.db.execution.DbExecutionImpl;
import org.activiti.pvm.ActivityBehavior;
import org.activiti.pvm.ActivityExecution;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.core.MessageBuilder;
import org.springframework.integration.gateway.SimpleMessagingGateway;

import java.util.Map;


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
 * @author Josh Long (based on an idea sketched out w/ Dave Syer and Tom Baeyens)
 * @see org.activiti.impl.bpmn.ReceiveTaskActivity this the ActivityBehavior impl that ships w/ Activiti that has the machinery to wake up when signal'd.
 */
public class ActivityBehaviorMessagingGateway extends SimpleMessagingGateway implements ActivityBehavior {

    public static final String WELL_KNOWN_EXECUTION_ID_HEADER_KEY = "activiti_spring_integration_executionId";

    /**
     * Injected from Spring or some other mechanism. Recommended approach is through a {@link org.activiti.impl.cfg.spring.ProcessEngineFactoryBean}
     */
    private volatile ProcessEngine processEngine;

    /**
     * Should we update the process variables based on the reply {@link org.springframework.integration.Message}'s {@link org.springframework.integration.MessageHeaders}?
     */
    private volatile boolean updateProcessVariablesFromResponseMessageHeaders;

    /**
     * Should we pass the workflow process variables as message headers when we send a message into the Spring Integration framework?
     */
    private volatile boolean forwardProcessVariablesAsMessageHeaders;

    public void setProcessEngine(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    public void setForwardProcessVariablesAsMessageHeaders(boolean forwardProcessVariablesAsMessageHeaders) {
        this.forwardProcessVariablesAsMessageHeaders = forwardProcessVariablesAsMessageHeaders;
    }

    public void setUpdateProcessVariablesFromResponseMessageHeaders(boolean updateProcessVariablesFromResponseMessageHeaders) {
        this.updateProcessVariablesFromResponseMessageHeaders = updateProcessVariablesFromResponseMessageHeaders;
    }

    public void execute(ActivityExecution execution) throws Exception {
        if (this.logger.isDebugEnabled()) {
            logger.debug("Entering execute(ActivityExecution) " + ToStringBuilder.reflectionToString(execution));
        }

        String executionId = null;

        if (execution instanceof DbExecutionImpl) {
            executionId = ((DbExecutionImpl) execution).getId();
        }

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
            logger.debug("Exiting execute(ActivityExecution) " + ToStringBuilder.reflectionToString(execution));
        }
    }
}
