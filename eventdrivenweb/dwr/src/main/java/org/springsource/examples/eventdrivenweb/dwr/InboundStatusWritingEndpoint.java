package org.springsource.examples.eventdrivenweb.dwr;

import org.springframework.integration.Message;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;


/**
 * Simple writer of time to a channel
 *
 * @author Josh Long
 */
public class InboundStatusWritingEndpoint extends MessageProducerSupport {

	@Scheduled( cron = "*/30 * * * * *")
	public void writeMessage() throws Throwable {
		Date now = new Date();
		Message<?> msg = MessageBuilder.withPayload("The current time is " +  now )
				.setHeader("now", now)
				.build() ;
	    sendMessage( msg );
	}
}
