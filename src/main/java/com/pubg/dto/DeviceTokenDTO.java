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
	private String devicetoken;
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
	public DeviceTokenDTO(String deviceType, String devicetoken, String userId){
		this.deviceType 		= deviceType;
		this.devicetoken		= devicetoken;
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
	 * @return the devicetoken
	 */
	public String getDevicetoken() {
		return devicetoken;
	}

	/**
	 * @param devicetoken the devicetoken to set
	 */
	public void setDevicetoken(String devicetoken) {
		this.devicetoken = devicetoken;
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
		return "DeviceTokenDTO [deviceType=" + deviceType + ", devicetoken=" + devicetoken + ", userId=" + userId + "]";
	}

}
