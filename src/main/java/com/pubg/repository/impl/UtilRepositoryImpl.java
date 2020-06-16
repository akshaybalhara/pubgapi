package com.pubg.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Repository;

import com.pubg.entity.NotificationEntity;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.repository.UtilRepository;

/**
 * UtilRepositoryImpl : Implementation class of UtilRepository.
 * 
 * @author Prolifics
 *
 */
@Repository
public class UtilRepositoryImpl implements UtilRepository, MessageConstants {
	
	/**
	 * EntityManager instance is injected by Spring Framework.
	 * 
	 * This is used to manage all the Persistent entities
	 * defined in the System.
	 */
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	/**
	 * getNotificationsOfUserByType returns all current and historical
	 * Notifications of a User based on specified type.
	 * @param empId
	 * 	<p>Employee Id of the User.</p>
	 * @param unActionedOnly
	 * 	<p>Flag to specify if only un-actioned item to return.</p>
	 * @return
	 * 	<p>List of <strong>NotificationEntity</strong> instances.
	 */
	public List<NotificationEntity> getNotificationsOfUserByType(String empId, boolean unActionedOnly){
		List<NotificationEntity> list = null;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		if(unActionedOnly){
			list = entityManager.createNamedQuery("getActiveNotificationsOfUser", NotificationEntity.class)
				    .setParameter(1, empId).getResultList();
		}else{
			list = entityManager.createNamedQuery("getAllNotificationsOfUser", NotificationEntity.class)
				    .setParameter(1, empId).getResultList();
		}
		if(null != list && !list.isEmpty()) {
			EntityTransaction entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			for (NotificationEntity notificationEntity : list) {
				String updateNotificationQuery = "FROM NotificationEntity where NOTIFICATION_ID = "+notificationEntity.getNotificationId();
				NotificationEntity notificationEntityUpdate = (NotificationEntity) entityManager.createQuery(updateNotificationQuery).getResultList().get(0);
				if(!notificationEntityUpdate.isActionTaken() && (notificationEntityUpdate.getNotification().contains("Accepted") || notificationEntityUpdate.getNotification().contains("Rejected"))) {
					notificationEntityUpdate.setActionTaken(true);
					entityManager.merge(notificationEntityUpdate);
				}
			}
			entityTransaction.commit();
			entityManager.clear();
			entityManager.close();
		}
		return list;
	}

}
