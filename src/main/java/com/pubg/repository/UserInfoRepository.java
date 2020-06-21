package com.pubg.repository;

import org.springframework.stereotype.Repository;

import com.pubg.dto.ChangePasswordDTO;
import com.pubg.dto.DeviceTokenDTO;
import com.pubg.entity.RegistrationEntity;
import com.pubg.entity.UserEntity;

@Repository
public interface UserInfoRepository {
	
	/**
	 * Retrieves Employee details based on his employee id.
	 * 
	 * @param employeeId
	 */
	public UserEntity getUserDetails(String userId);
	
	/**
	 *updateEmployeeProfile updates the profile information on an employee.
	 *
	 *@param userEntity
	 */
	public void updateUserProfile(UserEntity userEntity);

	/**
	 * updateProfilePassword updates the profile password of user.
	 * 
	 * @param changePassword
	 * @param isForgotPassword
	 * @return
	 */
	public void updateProfilePassword(ChangePasswordDTO changePassword, boolean isForgotPassword);
	
	/**
	 * updateDeviceToken updates the device token for push notifications.
	 * 
	 * @param deviceTokenDto
	 * @return
	 */
	public void updateDeviceToken(DeviceTokenDTO deviceTokenDto);

	/**
	 * createNewUser registers a new user.
	 * 
	 * @param registrationRequest
	 * @return
	 */
	public void createNewUser(RegistrationEntity registrationRequest,String otp);

	public void activateAccount(String userId, String otp);

}
