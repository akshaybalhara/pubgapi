package com.pubg.service;

import java.util.List;
import java.util.Map;

import com.pubg.entity.NotificationEntity;

/**
 * UtilService holds all the Service methods that are 
 * utilized for common services and are not relevant to
 * core System entities.
 *  
 * @author Prolifics
 *
 */
public interface UtilService {

	/**
	 * getAllRemindersOfUser returns all active Reminders
	 * of a User.
	 * @param empId
	 * 	<p>Employee Id of the User.</p>
	 * @return
	 * 	<p>List of <strong>NotificationEntity</strong> instances.
	 */
	public List<NotificationEntity> getAllRemindersOfUser(String empId);
	/**
	 * getAllRemindersOfUser returns all current and historical
	 * Notifications of a User.
	 * @param empId
	 * 	<p>Employee Id of the User.</p>
	 * @return
	 * 	<p>List of <strong>NotificationEntity</strong> instances.
	 */
	public List<NotificationEntity> getAllNotificationsOfUser(String empId);
	
	/**
	 * getApplicationDetails retrieves an application based on applicationType
	 * and identifier.
	 * 
	 * @param applicationType
	 * 	<p>The type of Application.</p>
	 * @param identifier
	 * 	<p>The identifer of the specific Application queried.</p>
	 * @return
	 */
	public Object getApplicationDetails(String applicationType, String identifier);
	
	/**
	 * getAllApprovers returns the map of all approvers
	 * based on the employeeId
	 * @param employeeId
	 * @return
	 */
	public Map<String,String> getAllApprovers(String employeeId,int levels);
}
