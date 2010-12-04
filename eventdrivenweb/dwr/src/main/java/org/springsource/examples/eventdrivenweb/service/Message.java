package org.springsource.examples.eventdrivenweb.service;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * simple holder for message information
 *
 * @author Josh Long
 * @since 1.0
 *
 */
public class Message implements Serializable , Comparable{

	/**
	 * when the message was added
	 */
	private Date dateOfMessage ;

	/**
	 * the contents of the message
	 */
	private String message;

	@Override
	public String toString() {
		return "Message{" +
				"dateOfMessage=" + dateOfMessage +
				", message='" + message + '\'' +
				'}';
	}

	public Message() {
	}

	public Message(Date dateOfMessage, String message) {	
		this.dateOfMessage = dateOfMessage;
		this.message = message;
	}

	public Date getDateOfMessage() {
		return dateOfMessage;
	}

	public void setDateOfMessage(Date dateOfMessage) {
		this.dateOfMessage = dateOfMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Message message1 = (Message) o;

		if (dateOfMessage != null ? !dateOfMessage.equals(message1.dateOfMessage) : message1.dateOfMessage != null) return false;
		if (message != null ? !message.equals(message1.message) : message1.message != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = dateOfMessage != null ? dateOfMessage.hashCode() : 0;
		result = 31 * result + (message != null ? message.hashCode() : 0);
		return result;
	}

	@Override
	 public int compareTo(Object o) {
	    return hashCode() - o.hashCode() ;
	}
}
