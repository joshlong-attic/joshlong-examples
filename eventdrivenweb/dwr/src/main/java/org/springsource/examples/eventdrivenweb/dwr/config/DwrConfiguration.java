package org.springsource.examples.eventdrivenweb.dwr.config;

import org.directwebremoting.spring.DwrNamespaceHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.dwr.AsyncHttpRequestHandlingMessageAdapter;

import javax.annotation.PostConstruct;


/**
 * Sets up the beans specific to the Atmosphere-specific configuration.
 *
 * @author Josh Long
 * @since 1.0
 */
@Configuration
public class DwrConfiguration extends WebConfiguration {

	@Value("#{out}") protected MessageChannel messageChannel;

	@Bean
	public AsyncHttpRequestHandlingMessageAdapter inboundDwrAdapter (){
		AsyncHttpRequestHandlingMessageAdapter inboundDwrAdapter = new AsyncHttpRequestHandlingMessageAdapter();
		inboundDwrAdapter.setRequestChannel(this.messageChannel);
		return inboundDwrAdapter ;
	}
}
