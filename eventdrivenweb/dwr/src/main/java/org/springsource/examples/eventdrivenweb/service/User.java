package org.springsource.examples.eventdrivenweb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListSet;


/**
 * Simple entity that we can use to handle chat scenarios
 *
 * @author Josh Long
 * @since 1.0
 */
public class User implements Serializable {
    /**
     * the collection of messages put into the chat
     */
    protected ConcurrentSkipListSet<Message> messages = new ConcurrentSkipListSet<Message>();

    /**
     * for synchronization purposes to guard against multiple concurrent accesses
     */
    private final Object monitor = new Object();

	/**
	 * the userId as created by the client
	 */
    private String userId;

	public User() {
	}

	public User(String userId) {
		this.userId = userId;
	}

	public Collection<Message> getMessages() {
        return this.messages;
    }

    public Message addMessage(String msg) {
        synchronized (this.monitor) {
            Message msgObj = new Message(new Date(), msg);
            this.messages.add(msgObj);
            return msgObj;
        }
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId='" + userId + '\'' +
				'}';
	}
}
