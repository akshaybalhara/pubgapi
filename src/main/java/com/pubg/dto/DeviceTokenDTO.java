package com.pubg.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Status Data Transfer Object.
 * 
 * @author Prolifics.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceTokenDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * The type of device.
	 */
	private String deviceType;
	/**
	 * Device token for push notifications.
	 */
	private String deviceToken;
	/**
	 * User Id.
	 */
	private String userId;
	
	/**
	 * Empty Constructor.
	 */
	public DeviceTokenDTO(){		
	}
	
	/**
	 * Parameterized Constructor.
	 */
	public DeviceTokenDTO(String deviceType, String deviceToken, String userId){
		this.deviceType 		= deviceType;
		this.deviceToken		= deviceToken;
		this.userId				= userId;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the deviceToken
	 */
	public String getDeviceToken() {
		return deviceToken;
	}

	/**
	 * @param devicetoken the deviceToken to set
	 */
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DeviceTokenDTO [deviceType=" + deviceType + ", deviceToken=" + deviceToken + ", userId=" + userId + "]";
	}

}
