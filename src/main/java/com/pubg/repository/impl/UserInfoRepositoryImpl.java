package com.pubg.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Repository;

import com.pubg.dto.DeviceTokenDTO;
import com.pubg.dto.ResetPasswordDTO;
import com.pubg.entity.AppUpdateEntity;
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
	public void updateUserProfile(UserEntity updatedUserEntity) {
		//UserEntity updatedUserEntity = updateUserEntity(newUserEntity);
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
	 * updateProfilePassword updates the profile password of user.
	 * 
	 * @param changePassword
	 * @return
	 */
	@Override
	public void updateProfilePassword(ResetPasswordDTO changePassword, boolean isForgotPassword) {
		UserEntity userEntity = getUserDetails(changePassword.getUserId());
		//String oldEncryptedPassword ="";
		String newEncryptedPassword ="";
		/*if(null!=changePassword.getOldPassword() && !changePassword.getOldPassword().isEmpty()){
			oldEncryptedPassword = PUBGGeneralUtils.getEncryptedText(changePassword.getOldPassword());
		}*/
		if(null!=changePassword.getNewPassword() && !changePassword.getNewPassword().isEmpty()){
			newEncryptedPassword = PUBGGeneralUtils.getEncryptedText(changePassword.getNewPassword());
		}
		
		/*if(!isForgotPassword && userEntity.getPassword().equals(oldEncryptedPassword) && !newEncryptedPassword.isEmpty()) {
			updatePassword(userEntity, newEncryptedPassword);
		}else*/ if(isForgotPassword && null!=userEntity){
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
		
		String updateUserInfoQuery = "FROM UserEntity where userId = '"+deviceTokenDto.getUserId()+"'";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			UserEntity userEntity = (UserEntity) entityManager.createQuery(updateUserInfoQuery).getResultList().get(0);
			userEntity.setDevice(deviceTokenDto.getDeviceType());
			userEntity.setDeviceToken(deviceTokenDto.getDeviceToken());
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
			entityManager.clear();
			entityManager.close();
			return true;
		}else {
			entityManager.clear();
			entityManager.close();
			return false;
		}
		
	}



	@Override
	public boolean checkPhone(String phone) {
		String updateAccountStatus = "FROM RegistrationEntity where phone = '"+phone+"'";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		if(entityManager.createQuery(updateAccountStatus).getResultList().size() > 0) {
			entityManager.clear();
			entityManager.close();
			return true;
		}else {
			entityManager.clear();
			entityManager.close();
			return false;
		}
		
	}



	@Override
	public boolean checkUserId(String userId) {
		String updateAccountStatus = "FROM RegistrationEntity where userId = '"+userId+"'";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		if(entityManager.createQuery(updateAccountStatus).getResultList().size() > 0) {
			entityManager.clear();
			entityManager.close();
			return true;
		}else {
			entityManager.clear();
			entityManager.close();
			return false;
		}
		
	}



	@SuppressWarnings("unchecked")
	@Override
	public AppUpdateEntity checkAppVersion() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String query = "SELECT COALESCE(MAX(id),1) FROM AppUpdateEntity";
		int id = (int) entityManager.createQuery(query).getResultList().get(0);
		query = "FROM AppUpdateEntity where id = "+id;
		List<AppUpdateEntity> list = (List<AppUpdateEntity>) entityManager.createQuery(query).getResultList();
		AppUpdateEntity appUpdateEntity = new AppUpdateEntity();
		if(list.size() > 0) {
			appUpdateEntity = list.get(0);
		}else {
			appUpdateEntity.setAppUrl("#");
			appUpdateEntity.setAppVersion(0);
			appUpdateEntity.setId(0);
		}
		entityManager.clear();
		entityManager.close(); 
		return appUpdateEntity;
	}



	@Override
	public UserEntity getUserByPubgUsername(String pubgUsername) {
		String selectQuery = "FROM UserEntity where pubgUsername = '"+pubgUsername+"'";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		UserEntity entity = (UserEntity) entityManager.createQuery(selectQuery).getResultList().get(0);
		entityManager.clear();
		entityManager.close();
		return entity;
	}
	
	
	

}
