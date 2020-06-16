package com.pubg.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * The User Id.
	 */
	private String userId;
	/**
	 * The Password.
	 */
	private String password;
	/**
	 * @return the employeeId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", password=" + password + "]";
	}
		
	
}
