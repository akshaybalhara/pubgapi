package com.pubg.repository.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Repository;

import com.pubg.dto.ChangePasswordDTO;
import com.pubg.dto.DeviceTokenDTO;
import com.pubg.entity.RegistrationEntity;
import com.pubg.entity.UserEntity;
import com.pubg.exception.PUBGBusinessException;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.repository.UserInfoRepository;
import com.pubg.util.PUBGGeneralUtils;

/**
 * UtilRepositoryImpl : Implementation class of UtilRepository.
 * 
 * @author Prolifics
 *
 */
@Repository
public class UserInfoRepositoryImpl implements UserInfoRepository, MessageConstants {
	
	/**
	 * EntityManager instance is injected by Spring Framework.
	 * 
	 * This is used to manage all the Persistent entities
	 * defined in the System.
	 */

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	/**
	 * Retrieves Employee details based on his employee id.
	 * 
	 * @param employeeId
	 */
	@Override
	public UserEntity getUserDetails(String userId) {
		UserEntity entity = new UserEntity();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entity = entityManager.find(UserEntity.class,userId);
		} catch (RuntimeException e) {
		    throw new PUBGBusinessException(NO_USER_EXIST, NO_USER_EXIST_MSG);
		} finally {
			entityManager.clear();
			entityManager.close();
		}
		return entity;
	}

	

	/**
	 *updateUserProfile updates the profile information on an employee.
	 *
	 *@param newUserEntity
	 */
	@Override
	public void updateUserProfile(UserEntity newUserEntity) {
		UserEntity updatedUserEntity = updateUserEntity(newUserEntity);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			//Start of transaction
			entityTransaction.begin();
			//merge method is used to update entities into their DB table.
			entityManager.merge(updatedUserEntity);
			entityTransaction.commit();
		}catch(RuntimeException e) {
			throw new PUBGBusinessException(SOMETHING_WENT_WRONG, SOMETHING_WENT_WRONG_MSG);
		}finally {
			entityManager.clear();
			entityManager.close();
		}
	}

	/**
	 *updateUserEntity updates the entity information on an employee.
	 *
	 *@param newUserEntity
	 */
	private UserEntity updateUserEntity(UserEntity newUserEntity) {
		UserEntity oldUserEntity = new UserEntity();
		if(null != newUserEntity && !newUserEntity.getUserId().isEmpty()) {
			/*
			 * oldUserEntity = getUserDetails(newUserEntity.getUserId()); if(null !=
			 * newUserEntity.getContact() && !newUserEntity.getContact().isEmpty()) {
			 * oldUserEntity.setContact(newUserEntity.getContact()); } if(null !=
			 * newUserEntity.getDob()) { oldUserEntity.setDob(newUserEntity.getDob()); }
			 * if(null != newUserEntity.getMartialStatus() &&
			 * !newUserEntity.getMartialStatus().isEmpty()) {
			 * oldUserEntity.setMartialStatus(newUserEntity.getMartialStatus()); }
			 */
		}else {
			throw new PUBGBusinessException(SOMETHING_WENT_WRONG, SOMETHING_WENT_WRONG_MSG);
		}
		return oldUserEntity;
	}

	/**
	 * updateProfilePassword updates the profile password of user.
	 * 
	 * @param changePassword
	 * @return
	 */
	@Override
	public void updateProfilePassword(ChangePasswordDTO changePassword, boolean isForgotPassword) {
		UserEntity userEntity = getUserDetails(changePassword.getEmployeeId());
		String oldEncryptedPassword ="";
		String newEncryptedPassword ="";
		if(null!=changePassword.getOldPassword() && !changePassword.getOldPassword().isEmpty()){
			oldEncryptedPassword = PUBGGeneralUtils.getEncryptedText(changePassword.getOldPassword());
		}
		if(null!=changePassword.getNewPassword() && !changePassword.getNewPassword().isEmpty()){
			newEncryptedPassword = PUBGGeneralUtils.getEncryptedText(changePassword.getNewPassword());
		}
		
		if(!isForgotPassword && userEntity.getPassword().equals(oldEncryptedPassword) && !newEncryptedPassword.isEmpty()) {
			updatePassword(userEntity, newEncryptedPassword);
		}else if(isForgotPassword && null!=userEntity){
			updatePassword(userEntity, newEncryptedPassword);
		}else {
			throw new PUBGBusinessException(INVALID_OLD_PASS_CODE, INVALID_OLD_PASS_MSG);
		}	
	}

	/**
	 * @param userEntity
	 * @param newEncryptedPassword
	 */
	private void updatePassword(UserEntity userEntity, String newEncryptedPassword) {
		userEntity.setPassword(newEncryptedPassword);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			//Start of transaction
			entityTransaction.begin();
			//merge method is used to update entities into their DB table.
			entityManager.merge(userEntity);
			entityTransaction.commit();
		}catch(RuntimeException e) {
			throw new PUBGBusinessException(SOMETHING_WENT_WRONG, SOMETHING_WENT_WRONG_MSG);
		}finally {
			entityManager.clear();
			entityManager.close();
		}
	}

	/**
	 * updateDeviceToken updates the device token for push notifications.
	 * 
	 * @param deviceTokenDto
	 * @return
	 */
	@Override
	public void updateDeviceToken(DeviceTokenDTO deviceTokenDto) {
		
		String updateUserInfoQuery = "FROM UserEntity where EMPLOYEE_ID = '"+deviceTokenDto.getUserId()+"'";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			UserEntity userEntity = (UserEntity) entityManager.createQuery(updateUserInfoQuery).getResultList().get(0);
			//userEntity.setDevice(deviceTokenDto.getDeviceType());
			//userEntity.setDeviceToken(deviceTokenDto.getDevicetoken());
			//Start of transaction
			entityTransaction.begin();
			//merge method is used to update entities into their DB table.
			entityManager.merge(userEntity);
			entityTransaction.commit();
		}catch(RuntimeException e) {
			throw new PUBGBusinessException(SOMETHING_WENT_WRONG, SOMETHING_WENT_WRONG_MSG);
		}finally {
			entityManager.clear();
			entityManager.close();
		}
	}



	@Override
	public void createNewUser(RegistrationEntity registrationRequest,String otp) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String encryptedPassword = PUBGGeneralUtils.getEncryptedText(registrationRequest.getPassword());
		registrationRequest.setPassword(encryptedPassword);
		registrationRequest.setOtp(otp);
		//This will get updated after email has been sent successfully to user.
		registrationRequest.setVerificationLink(PENDING);
		registrationRequest.setStatus(INACTIVE);
		registrationRequest.setRole(USER_ROLE);
		registrationRequest.setSubmissionDate(new Date());
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			//Start of transaction
			entityTransaction.begin();
			//persist method is used to do insertion of entities into their DB table.
			System.out.println(registrationRequest);
			entityManager.persist(registrationRequest);
			//commit will actually make this transaction persist in DB.
			entityTransaction.commit();
		} catch (RuntimeException e) {
		    if (entityTransaction.isActive()) {
		        entityTransaction.rollback();
		    }
		    e.printStackTrace();
		    throw new PUBGBusinessException(SOMETHING_WENT_WRONG,SOMETHING_WENT_WRONG_MSG);
		} finally {
			entityManager.clear();
		    entityManager.close();
		}
		
	}



	@Override
	public void activateAccount(String userId, String otp) {
		String updateAccountStatus = "FROM RegistrationEntity where userId = '"+userId+"'";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			RegistrationEntity userEntity = (RegistrationEntity) entityManager.createQuery(updateAccountStatus).getResultList().get(0);
			if(userEntity.getOtp().equals(otp)) {
				userEntity.setStatus(ACTIVE);
			}else {
				throw new PUBGBusinessException("OTP_001", "Invalid OTP recieved! Can't activate your account right now.");
			}
			//Start of transaction
			entityTransaction.begin();
			//merge method is used to update entities into their DB table.
			entityManager.merge(userEntity);
			entityTransaction.commit();
		}catch(RuntimeException e) {
			e.printStackTrace();
		}finally {
			entityManager.clear();
			entityManager.close();
		}
		
	}



	@Override
	public boolean checkEmail(String email) {
		String updateAccountStatus = "FROM RegistrationEntity where email = '"+email+"'";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		if(entityManager.createQuery(updateAccountStatus).getResultList().size() > 0) {
			return true;
		}else {
			return false;
		}
		
	}



	@Override
	public boolean checkPhone(String phone) {
		String updateAccountStatus = "FROM RegistrationEntity where phone = '"+phone+"'";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		if(entityManager.createQuery(updateAccountStatus).getResultList().size() > 0) {
			return true;
		}else {
			return false;
		}
		
	}



	@Override
	public boolean checkUserId(String userId) {
		String updateAccountStatus = "FROM RegistrationEntity where userId = '"+userId+"'";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		if(entityManager.createQuery(updateAccountStatus).getResultList().size() > 0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	

}
