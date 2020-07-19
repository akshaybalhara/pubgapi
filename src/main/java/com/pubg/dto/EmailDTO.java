package com.pubg.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Status Data Transfer Object.
 * 
 * @author Prolifics.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String sendTo;
	
	private String subject;
	
	private String message;
	
	private String userId;
	
	private String data;
	
	/**
	 * Empty Constructor.
	 */
	public EmailDTO(){		
	}
	
	/**
	 * Parameterized Constructor.
	 */
	public EmailDTO(String sendTo, String subject, String message){
		this.sendTo 		= sendTo;
		this.subject		= subject;
		this.message	= message;
	}

	/**
	 * @return the sendTo
	 */
	public String getSendTo() {
		return sendTo;
	}

	/**
	 * @param sendTo the sendTo to set
	 */
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "EmailDTO [sendTo=" + sendTo + ", subject=" + subject + ", message=" + message + "]";
	}

}
