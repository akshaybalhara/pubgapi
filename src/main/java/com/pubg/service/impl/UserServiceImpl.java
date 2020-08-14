package com.pubg.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubg.dto.DeviceTokenDTO;
import com.pubg.dto.ResetPasswordDTO;
import com.pubg.dto.StatusDTO;
import com.pubg.entity.AppUpdateEntity;
import com.pubg.entity.RegistrationEntity;
import com.pubg.entity.UserEntity;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.repository.UserInfoRepository;
import com.pubg.service.BaseService;
import com.pubg.service.UserService;


/**
 * UserServiceImpl holds all the Service methods that are 
 * utilized to get User Information and User Authentication Flow.
 *  
 * @author Prolifics
 *
 */
@Service
public class UserServiceImpl  extends BaseService implements UserService, MessageConstants{

	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	/**
	 * processForgotPassword used to reset the old password.
	 * 
	 * @param empId
	 * @param pin
	 * @return
	 */
	@Override
	public StatusDTO processForgotPassword(String userId,String pin) {
		ResetPasswordDTO changePassword = new ResetPasswordDTO();
		changePassword.setUserId(userId);
		changePassword.setNewPassword(pin);
		userInfoRepository.updateProfilePassword(changePassword,true);
		StatusDTO status = new StatusDTO(true,"PASS_001","Your password updated successfully.");
		return status;
	}

	/**
	 * getUserProfile returns all information related to user profile.
	 * 
	 * @param empId
	 * @return
	 */
	@Override
	public UserEntity getUserProfile(String userId) {
		return userInfoRepository.getUserDetails(userId);
	}

	/**
	 * updateUserProfile updates the profile information on an employee.
	 * 
	 * @param userEntity
	 * @return
	 */
	@Override
	public StatusDTO updateUserProfile(UserEntity userEntity) {
		userInfoRepository.updateUserProfile(userEntity);
		StatusDTO status = new StatusDTO(true,"USR_001","User profile updated successfully.");
		return status;
	}
	
	/**
	 * changePassword updates the profile password of user.
	 * 
	 * @param changePassword
	 * @return
	 */
	@Override
	public StatusDTO changePassword(ResetPasswordDTO changePassword) {
		userInfoRepository.updateProfilePassword(changePassword,false);
		StatusDTO status = new StatusDTO(true,"","");
		return status;
	}

	/**
	 * updateDeviceToken updates the device token for push notifications.
	 * 
	 * @param deviceTokenDto
	 * @return
	 */
	@Override
	public StatusDTO updateDeviceToken(DeviceTokenDTO deviceTokenDto) {
		userInfoRepository.updateDeviceToken(deviceTokenDto);
		StatusDTO status = new StatusDTO(true,"TOK_001","Device Token updated successfully.");
		return status;
	}

	@Override
	public StatusDTO registerNewUser(RegistrationEntity registrationRequest, String otp) {
		logger.info("Entering into UserServiceImpl.registerNewUser()");
		userInfoRepository.createNewUser(registrationRequest,otp);
		StatusDTO status = new StatusDTO(true,"REG_001","Registered successfully! An email has been sent to your registered email("
				+registrationRequest.getEmail()+ "). Please check your email and activate your account by clicking the link inside your mail.");
		logger.info("Exiting UserServiceImpl.registerNewUser()");
		return status;
	}

	@Override
	public void activateAccount(String userId, String otp) {
		logger.info("Entering into UserServiceImpl.activateAccount()");
		userInfoRepository.activateAccount(userId,otp);
		System.out.println("Account activated successfully.");
		logger.info("Exiting UserServiceImpl.activateAccount()");
	}

	@Override
	public boolean checkEmail(String email) {
		return userInfoRepository.checkEmail(email);
	}

	@Override
	public boolean checkPhone(String phone) {
		return userInfoRepository.checkPhone(phone);
	}

	@Override
	public boolean checkUserId(String userId) {
		return userInfoRepository.checkUserId(userId);
	}

	@Override
	public AppUpdateEntity checkAppUpdate() {
		return userInfoRepository.checkAppVersion();
	}

	@Override
	public StatusDTO updateExistingUser(RegistrationEntity registrationRequest, String userId, String pubgUsername) {
		logger.info("Entering into UserServiceImpl.updateExistingUser()");
		StatusDTO status = new StatusDTO();
		if(userId!=null && pubgUsername!=null) {
			UserEntity userEntity = getUserProfile(userId);
			userEntity.setPubgUsername(pubgUsername);
			status = updateUserProfile(userEntity);
			logger.info("Updating Pubg username only....");
		}else {
			UserEntity userEntity = getUpdatedUserEntity(registrationRequest);
			status = updateUserProfile(userEntity);
			logger.info("Updating full user profile....");
		}
		logger.info("Exiting UserServiceImpl.updateExistingUser()");
		return status;
	}

	private UserEntity getUpdatedUserEntity(RegistrationEntity registrationRequest) {
		UserEntity userEntity = getUserProfile(registrationRequest.getUserId());
		if(registrationRequest!=null) {
			userEntity.setPhone(registrationRequest.getPhone());
			userEntity.setPubgUsername(registrationRequest.getPubgUsername());
			userEntity.setEmail(registrationRequest.getEmail());
		}
		return userEntity;
	}

	@Override
	public UserEntity getUserProfileByPubgUsername(String pubgUsername) {
		UserEntity entity = userInfoRepository.getUserByPubgUsername(pubgUsername);
		return entity;
	}

}
