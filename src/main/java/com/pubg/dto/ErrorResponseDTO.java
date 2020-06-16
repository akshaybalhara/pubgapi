package com.pubg.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Data Transfer object which will be utilized to
 * send Error Response to the calling client.
 * 
 * @author Prolifics.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * The error Code.
	 */
	private String errorCode;
	/**
	 * The error Message.
	 */
	private String message;
	/**
	 * The actual Exception Message returned by the System.
	 */
	private String cause;
	
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
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
	 * @return the cause
	 */
	public String getCause() {
		return cause;
	}
	/**
	 * @param cause the cause to set
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}
	
}
