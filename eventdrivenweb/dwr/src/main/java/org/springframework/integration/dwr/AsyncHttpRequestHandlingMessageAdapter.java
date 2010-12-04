package org.springframework.integration.dwr;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;

import org.directwebremoting.proxy.dwr.Util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;

import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.MessagingException;
import org.springframework.integration.channel.AbstractPollableChannel;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.core.MessageHandler;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.scheduling.PollerMetadata;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.ServletContextAware;

import java.io.IOException;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This is a Spring Integration adapter that's based on the amazing work integrating Atmosphere by Jeremy Grelle,
 *
 * This provides analogous support using the DWR (http://directwebremoting.org/) project which has excellent Spring support already.
 *
 * NB: the Atmosphere support is far more flexible. Use this adapter if you've already got DWR and simply want a clean, messaging-oriented way of solving problems using it.
 *
 * @since 1.0
 * @author Josh Long
 */
public class AsyncHttpRequestHandlingMessageAdapter implements MessageHandler, BeanFactoryAware, ServletContextAware, InitializingBean, HttpRequestHandler {

	/**
     * if configured, and if the {@link org.springframework.integration.MessageChannel} requires it, then the {@link #endpoint} will use it.
     */
    protected PollerMetadata pollerMetadata;

    /**
     * the bean factory in which this bean was instantiated
     */
    protected BeanFactory beanFactory;

    /**
     * the channel from which messages are expected
     */
    protected MessageChannel requestChannel;

    /**
     * we need this to do any extra web request/response work with the servlet
     */
    protected ServletContext servletContext;

    /**
     * DWR specific construct to facilitate push messaging
     */
    protected ServerContext serverContext;

    /**
     * the endpoint that subscribes to messages from the #channel
     */
    protected AbstractEndpoint endpoint;

    /**
     * whether or not we should extract the payload to use as the argument to the callback function.
     * If you don't use this, then you'll need to be sure that the entire {@link org.springframework.integration.Message}
     * object can be converted into JSON. To do so, you might need to register a DWR converter beyond the basic 'bean' one.
     */
    protected boolean extractPayload = true;

    /**
     * the default function to invoke
     */
    protected String defaultCallbackFunctionName = "handleMessage";

    @SuppressWarnings({"unused"})
    public void setRequestChannel(MessageChannel requestChannel) {
        this.requestChannel = requestChannel;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {

	    if(null == this.serverContext)
		    serverContext = ServerContextFactory.get(this.servletContext);

		if(this.serverContext!=null){
			MessageHeaders headers = message.getHeaders();

			String callbackFunction = (String) headers.get(DwrConstants.DWR_SCRIPT_FUNCTION_NAME);

			if (!StringUtils.hasText(callbackFunction)) {
				callbackFunction = this.defaultCallbackFunctionName;
			}

			Collection<ScriptSession> sessions = serverContext.getAllScriptSessions();

			Object param = this.extractPayload ? message.getPayload() : message;

			Util util = new Util(sessions);
			util.addFunctionCall(callbackFunction, param);
			
		}
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.requestChannel, "the 'requestChannel' can't be null");
        Assert.notNull(this.servletContext, "the 'servletContext' can't be null");



        ConsumerEndpointFactoryBean consumerEndpointFactoryBean = new ConsumerEndpointFactoryBean();
        consumerEndpointFactoryBean.setInputChannel(this.requestChannel);
        consumerEndpointFactoryBean.setBeanClassLoader(ClassLoader.getSystemClassLoader());
        consumerEndpointFactoryBean.setBeanName(AsyncHttpRequestHandlingMessageAdapter.class.getName() + "DwrConsumer");
        consumerEndpointFactoryBean.setHandler(this);
        consumerEndpointFactoryBean.setBeanFactory(this.beanFactory);

        if (this.requestChannel instanceof AbstractPollableChannel) {
            Assert.notNull(this.pollerMetadata, "the 'pollerMetadata' must not be null when using pollable channels (" + AbstractPollableChannel.class.getName() + ")");
            consumerEndpointFactoryBean.setPollerMetadata(this.pollerMetadata);
        }

        consumerEndpointFactoryBean.afterPropertiesSet();
        endpoint = consumerEndpointFactoryBean.getObject();
	    endpoint.start() ;


    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // .. 
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory)
        throws BeansException {
        this.beanFactory = beanFactory;
    }
}
/*

public class Chat implements Serializable, InitializingBean, ServletContextAware {
    private Logger logger = Logger.getLogger(Chat.class);
    private final Queue<Message> record = new LinkedBlockingQueue<Message>();
    private ServletContext servletContext;
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();

    @Scheduled(fixedRate = 10 * 1000)
    protected void messageLoop() throws Throwable {
        say("jlong", "The time is " + System.currentTimeMillis());
    }

    protected void deliverMessageInIndependantThread(ServletContext ctx, Message message) {
        if (this.servletContext != null) {
            logger.info("deliverMessageInIndependantThread(" + message.toString() + ")");

            ServerContext serverContext = ServerContextFactory.get(ctx);

            if (null != serverContext) {
                Collection<ScriptSession> sessions = serverContext.getAllScriptSessions();
                Util util = new Util(sessions);
                util.addFunctionCall("processMessage", message);
            }
        }
    }

    public void say(String userid, String msg) {
        Message msgObj;

        synchronized (record) {
            msgObj = addOrGetUser(userid).addMessage(msg);
            this.record.add(msgObj);
        }

        deliverMessageInIndependantThread(this.servletContext, msgObj);
    }

    public User addOrGetUser(String userId) {
        users.putIfAbsent(userId, new User(userId));

        return users.get(userId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("servletContext: " + ((null == this.servletContext) ? "yes" : "no"));
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
*/
