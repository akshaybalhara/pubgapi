package com.pubg.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Push Notification Response Data Transfer Object.
 * 
 * @author Prolifics.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PushNotificationResponseDTO  implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * The status code of the Notification Delivery.
	 */
	private int status;
	/**
	 * The status message of Notification Delivery.
	 */
    private String message;
    
    /**
     * Default Constructor
     */
    public PushNotificationResponseDTO() {
    }

    /**
     * Parameterized Constructor
     * 
     */
    public PushNotificationResponseDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }
    
    
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
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
}
