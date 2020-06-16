package com.pubg.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Status Data Transfer Object.
 * 
 * @author Prolifics.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * Flag to determine if the Operation was successful.
	 */
	private boolean succeeded;
	/**
	 * The Status Code.
	 */
	private String statusCode;
	/**
	 * The Status Message.
	 */
	private String statusMessage;
	
	/**
	 * Empty Constructor.
	 */
	public StatusDTO(){		
	}
	
	/**
	 * Parameterized Constructor.
	 */
	public StatusDTO(boolean succeeded, String statusCode, String statusMessage){
		this.succeeded 		= succeeded;
		this.statusCode		= statusCode;
		this.statusMessage	= statusMessage;
	}

	/**
	 * @return the succeeded
	 */
	public boolean isSucceeded() {
		return succeeded;
	}

	/**
	 * @param succeeded the succeeded to set
	 */
	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public String toString() {
		return "StatusDTO [succeeded=" + succeeded + ", statusCode=" + statusCode + ", statusMessage=" + statusMessage
				+ "]";
	}

}
