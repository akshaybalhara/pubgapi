package com.pubg.service;

import com.pubg.dto.DeviceTokenDTO;
import com.pubg.dto.ResetPasswordDTO;
import com.pubg.dto.StatusDTO;
import com.pubg.entity.AppUpdateEntity;
import com.pubg.entity.RegistrationEntity;
import com.pubg.entity.UserEntity;

/**
 * UserService holds all the Service methods that are 
 * utilized in the User Authentication and User Profile Flow.
 *  
 * @author Prolifics
 *
 */
public interface UserService {
	
	/**
	 * processForgotPassword used to retrieve or change the old password.
	 * 
	 * @param empId
	 * @param pin
	 * @return
	 */
	public StatusDTO processForgotPassword(String empId, String pin);
	
	/**
	 * getUserProfile returns all information related to user profile.
	 * 
	 * @param empId
	 * @return
	 */
	public UserEntity getUserProfile(String empId);
	
	
	/**
	 * updateUserProfile updates the profile information on an employee.
	 * 
	 * @param entity
	 * @return
	 */
	public StatusDTO updateUserProfile(UserEntity entity);
	
	/**
	 * changePassword updates the profile password of user.
	 * 
	 * @param changePassword
	 * @return
	 */
	public StatusDTO changePassword(ResetPasswordDTO changePassword);

	/**
	 * updateDeviceToken updates the device token for push notifications.
	 * 
	 * @param deviceTokenDto
	 * @return
	 */
	public StatusDTO updateDeviceToken(DeviceTokenDTO deviceTokenDto);

	/**
	 * registerNewUser registers a new user.
	 * 
	 * @param registrationRequest
	 * @return
	 */
	public StatusDTO registerNewUser(RegistrationEntity registrationRequest,String otp);

	public void activateAccount(String userId, String otp);

	public boolean checkEmail(String email);

	public boolean checkPhone(String phone);

	public boolean checkUserId(String userId);

	public AppUpdateEntity checkAppUpdate();

	public StatusDTO updateExistingUser(RegistrationEntity registrationRequest, String userId, String pubgUsername);
	

}
