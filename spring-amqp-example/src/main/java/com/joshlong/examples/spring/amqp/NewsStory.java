package com.joshlong.examples.spring.amqp;

import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;

import java.util.Date;


/**
 * @author Josh Long
 */
public class NewsStory implements Serializable {
    private String title;
    private String body;
    private Date dateline;

    @Override
    public String toString() {
        return new ToStringCreator(this)
		        .append("title", this.title)
		        .append("body", this.body)
		        .append("dateline", this.dateline)
		        .toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDateline() {
        return dateline;
    }

    public void setDateline(Date dateline) {
        this.dateline = dateline;
    }
}
