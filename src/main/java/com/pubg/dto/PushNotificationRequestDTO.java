package com.pubg.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Push Notification Request Data Transfer Object.
 * 
 * @author Prolifics.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PushNotificationRequestDTO  implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * The title of the Notification.
	 */
	private String title;
	/**
	 * The actual Notification message.
	 */
    private String message;
    /**
     * The topic to push the Notification to.
     */
    private String topic;
    /**
     * Any specific Device token to which the notification needs to be pushed.
     */
    private String token;
    
    /**
     * Default Constructor
     */
    public PushNotificationRequestDTO() {
    }

    /**
     * Parameterized Constructor
     */
    public PushNotificationRequestDTO(String title, String messageBody, String topicName) {
        this.title = title;
        this.message = messageBody;
        this.topic = topicName;
    }
    
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}
	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
}
