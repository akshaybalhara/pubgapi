package com.pubg.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubg.entity.NotificationEntity;
import com.pubg.repository.UtilRepository;
import com.pubg.service.BaseService;
import com.pubg.service.UtilService;

/**
 * UtilServiceImpl : Implementation class of UtilService.
 * 
 * @author Prolifics
 *
 */
@Service
public class UtilServiceImpl extends BaseService implements UtilService {
	
	private UtilRepository utilRepository; 

	/**
	 * getAllRemindersOfUser returns all active Reminders
	 * of a User.
	 * @param empId
	 * 	<p>Employee Id of the User.</p>
	 * @return
	 * 	<p>List of <strong>NotificationEntity</strong> instances.
	 */
	@Override
	public List<NotificationEntity> getAllRemindersOfUser(String empId){
		if(empId!=null && !empId.isEmpty()){
			return utilRepository.getNotificationsOfUserByType(empId,true);
		}
		return null;
	}
	/**
	 * getAllNotificationsOfUser returns all current and historical
	 * Notifications of a User.
	 * @param empId
	 * 	<p>Employee Id of the User.</p>
	 * @return
	 * 	<p>List of <strong>NotificationEntity</strong> instances.
	 */
	@Override
	public List<NotificationEntity> getAllNotificationsOfUser(String empId){
		if(empId!=null && !empId.isEmpty()){
			return utilRepository.getNotificationsOfUserByType(empId,false);
		}
		return null;
	}
	
	
	/**
	 * @param utilRepository the utilRepository to set
	 */
	@Autowired
	public void setUtilRepository(UtilRepository utilRepository) {
		this.utilRepository = utilRepository;
	}
	@Override
	public Object getApplicationDetails(String applicationType, String identifier) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, String> getAllApprovers(String employeeId, int levels) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
