package org.springsource.examples.eventdrivenweb.example;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dwr.DwrMessageHeaders;
import org.springframework.integration.support.MessageBuilder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * represents a stateful chat room. this is reasonably thread-safe, though I haven't proven it.
 * <p/>
 * this is a simple example to demonstrate the server-side push
 *
 * @author Josh Long
 * @since 1.0
 */
public class Chat {
    
	private MessagingTemplate messagingTemplate = new MessagingTemplate();

	private MessageChannel rosterChannel;

	private MessageChannel messageChannel;

	private MessageChannel directMessageChannel ;

	final private ConcurrentHashMap<String, String> users = new ConcurrentHashMap<String, String>();

	final private List<String> chatMessages = new ArrayList<String>();

	/**
	 * cleans up all chat state so we can reset / retest
	 */
	public void reset (){
		this.users.clear();
		this.chatMessages.clear();
	}

	/**
	 * channel on which direct messages should be issued
	 */
	public void setDirectMessageChannel(MessageChannel directMessageChannel) {
		this.directMessageChannel = directMessageChannel;
	}

    /**
     * this channel is used to route messages concerning new members joining a chat
     */
    public void setRosterChannel(MessageChannel rosterChannel) {
        this.rosterChannel = rosterChannel;
    }

    /**
     * the channel used to route messages to members in the chat
     */
    public void setMessageChannel(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    /**
     * add a user to the chat room
     */
    public void join(String user) {
        WebContext wc = WebContextFactory.get();

        ScriptSession session = wc.getScriptSession();

        synchronized (this.users) {
            if (this.users.containsKey(user)) {
                this.users.remove(user);
            }

            this.users.putIfAbsent(user, session.getId());
        }

	    // broadcast it
        Message<?> rosterEvent = MessageBuilder.withPayload(user).build();
        this.messagingTemplate.send(this.rosterChannel, rosterEvent);
    }

    /**
     * return the users logged into the system
     */
    public Set<String> getUsers() {
        Set<String> usrs = new HashSet<String>();

        for (String k : this.users.keySet()) {
            usrs.add(k + ":" + this.users.get(k));
        }

        return usrs;
    }

    /**
     * sends a message to a user directly
     */
    public void directMessage(String user, String msg) {
        String sessionId = this.users.get(user);
        Message<?> msgObject = MessageBuilder.withPayload(msg).setHeader(DwrMessageHeaders.DWR_TARGET_SESSION_ID, sessionId).build();
        this.messagingTemplate.send(this.directMessageChannel, msgObject);
    }

    /**
     * transcript of the content in the chat
     */
    public Collection<String> getChatMessages() {
        return new ArrayList<String>(this.chatMessages);
    }

    /**
     * sends a message to all the members in chat
     */
    public void groupMessage(String msg) {
        synchronized (this.chatMessages) {
            this.chatMessages.add(msg);
        }

        Message<?> msgObject = MessageBuilder.withPayload(msg).build();
        this.messagingTemplate.send(this.messageChannel, msgObject);
    }

    public void logout(String u) {
        WebContext wc = WebContextFactory.get();

        synchronized (this.users) {
            ScriptSession scriptSession = wc.getScriptSessionById(this.users.get(u));

            if ((scriptSession != null) && !scriptSession.isInvalidated()) {
                scriptSession.invalidate();
            }
        }
    }
}
