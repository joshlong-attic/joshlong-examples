package org.springsource.examples.eventdrivenweb.dwr.config;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.integration.MessageChannel;
import org.springframework.integration.dwr.AsyncHttpRequestHandlingMessageAdapter;

import org.springsource.examples.eventdrivenweb.example.Chat;


/**
 * Sets up the beans specific to the Atmosphere-specific configuration.
 *
 * @author Josh Long
 * @since 1.0
 */
@Configuration
public class DwrConfiguration extends WebConfiguration {
    /**
     * the channel to which message events should be sent
     */
    @Value("#{messageChannel}")
    protected MessageChannel messages;

    /**
     * the channel to which roster events should be sent
     */
    @Value("#{rosterChannel}")
    protected MessageChannel roster;

    @Bean
    public Chat chat() {
        Chat chat = new Chat();
        chat.setMessageChannel(this.messages);
        chat.setRosterChannel(this.roster);

        return chat;
    }

    @Bean
    public AsyncHttpRequestHandlingMessageAdapter messageAdapter() {
        AsyncHttpRequestHandlingMessageAdapter inboundDwrAdapter = new AsyncHttpRequestHandlingMessageAdapter();
        inboundDwrAdapter.setRequestChannel(this.messages);
	    inboundDwrAdapter.setDefaultCallbackFunctionName( "handleMessage");
        return inboundDwrAdapter;
    }

    @Bean
    public AsyncHttpRequestHandlingMessageAdapter rosterAdapter() {
        AsyncHttpRequestHandlingMessageAdapter inboundDwrAdapter = new AsyncHttpRequestHandlingMessageAdapter();
        inboundDwrAdapter.setDefaultCallbackFunctionName("handleRoster");
        inboundDwrAdapter.setRequestChannel(this.roster);

        return inboundDwrAdapter;
    }
}
