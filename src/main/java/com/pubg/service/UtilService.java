package com.pubg.service;

import com.pubg.dto.EmailDTO;

/**
 * UtilService holds all the Service methods that are 
 * utilized for common services and are not relevant to
 * core System entities.
 *  
 * @author Prolifics
 *
 */
public interface UtilService {	
	
	public void sendEmailWithAttachment(EmailDTO emailRequest,String appType);
}
