package com.pubg.repository;

import java.util.List;

import com.pubg.entity.NotificationEntity;

/**
 * UtilRepository encapsulates all database operations
 * related to utility functionalities of the System.
 * 
 * @author Prolifics
 *
 */
public interface UtilRepository {
	
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
	public List<NotificationEntity> getNotificationsOfUserByType(String empId, boolean unActionedOnly);

}
