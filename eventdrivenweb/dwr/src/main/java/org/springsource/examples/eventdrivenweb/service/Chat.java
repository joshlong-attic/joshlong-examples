package org.springsource.examples.eventdrivenweb.service;

import org.apache.log4j.Logger;

import org.directwebremoting.*;

import org.directwebremoting.extend.ScriptSessionManager;

import org.directwebremoting.impl.DefaultScriptSessionManager;

import org.directwebremoting.proxy.dwr.Util;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.Serializable;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletContext;


/**
 * simple chat example to prove everything works
 *
 * @author Josh Long
 */
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
