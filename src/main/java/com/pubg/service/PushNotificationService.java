package com.pubg.service;

import java.util.Map;

import com.pubg.dto.PushNotificationRequestDTO;

/**
 * PushNotificationService holds methods which assists in pushing
 * Push Notification to User's.
 *  
 * @author Prolifics
 *
 */
public interface PushNotificationService {
	/**
	 * sendPushNotificationToTopic sends a Push Notification
	 * to a specified Topic.
	 * 
	 * @param request
	 * 	<p>The Request object encapsulating Message, Topic etc.</p>
	 */
	public void sendPushNotificationToTopic(PushNotificationRequestDTO request);
	/**
	 * sendPushNotificationToTopic sends a Push Notification
	 * to a specified Topic. It also sends a data payload to
	 * take an action.
	 * 
	 * @param request
	 * 	<p>The Request object encapsulating Message, Topic etc.</p>
	 * @param
	 * 	<p>The Map object encapsulating Payload data.</p>
	 */
	public void sendPushNotificationToTopicWithData(PushNotificationRequestDTO request,Map<String, String> data);
	/**
	 * sendPushNotificationToToken sends a Push Notification
	 * to a specified Device with a Token.
	 * 
	 * @param request
	 * 	<p>The Request object encapsulating Message, Device Token etc.</p>
	 */
	public void sendPushNotificationToToken(PushNotificationRequestDTO request);
	/**
	 * sendPushNotificationToTokenWithData sends a Push Notification
	 * to a specified Device with a Token. It also sends a data payload to
	 * take an action.
	 * 
	 * @param request
	 * 	<p>The Request object encapsulating Message, Token etc.</p>
	 * @param
	 * 	<p>The Map object encapsulating Payload data.</p>
	 */
	public void sendPushNotificationToTokenWithData(PushNotificationRequestDTO request,Map<String, String> data);
	 
	/**
	 * sendSamplePushNotification sends a sample Notification to a default
	 * Topic and it in turn is delivered to all devices subscribed to that 
	 * Topic. 
	 */
	public void sendSamplePushNotification();
	
}
