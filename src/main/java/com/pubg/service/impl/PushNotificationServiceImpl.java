package com.pubg.service.impl;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.pubg.dto.PushNotificationRequestDTO;
import com.pubg.service.PushNotificationService;
import com.pubg.util.NotificationParameter;

/**
 * PushNotificationServiceImpl : Implementation class of PushNotificationService.
 * 
 * @author Prolifics
 */
@Service
public class PushNotificationServiceImpl implements PushNotificationService {
	/**
	 * Location of Firebase Configuration File.
	 */
	@Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;
	
	/**
	 * Default Notification Sample Data.
	 */
	@Value("#{${app.notifications.defaults}}")
    private Map<String, String> defaults;
	
	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * sendPushNotificationToTopic sends a Push Notification
	 * to a specified Topic.
	 * 
	 * @param request
	 * 	<p>The Request object encapsulating Message, Topic etc.</p>
	 */
	@Override
	public void sendPushNotificationToTopic(PushNotificationRequestDTO request){
		Message message = getPreconfiguredMessageWithoutData(request);
		String response = sendAndGetResponse(message);
		logger.info("Sent message without data. Topic: " + request.getTopic() + ", " + response);
	}
	
	/**
	 * sendPushNotificationToTopic sends a Push Notification
	 * to a specified Topic.
	 * 
	 * @param request
	 * 	<p>The Request object encapsulating Message, Topic etc.</p>
	 * @param
	 * 	<p>The Map object encapsulating Payload data.</p>
	 */
	@Override
	public void sendPushNotificationToTopicWithData(PushNotificationRequestDTO request,Map<String, String> data){
		Message message = getPreconfiguredMessageWithData(data, request);
		String response = sendAndGetResponse(message);
		logger.info("Sent message without data. Topic: " + request.getTopic() + ", " + response);		
	}
	
	/**
	 * sendPushNotificationToToken sends a Push Notification
	 * to a specified Device with a Token.
	 * 
	 * @param request
	 * 	<p>The Request object encapsulating Message, Device Token etc.</p>
	 */
	@Override
	public void sendPushNotificationToToken(PushNotificationRequestDTO request){
		Message message = getPreconfiguredMessageToToken(request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: " + request.getToken() + ", " + response);
	}	
	
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
	public void sendPushNotificationToTokenWithData(PushNotificationRequestDTO request,Map<String, String> data){
		Message message = getPreconfiguredMessageWithData(data, request);
		String response = sendAndGetResponse(message);
		logger.info("Sent message to token with data.  Device token: " + request.getToken() + ", " + response);
	}
	 
	/**
	 * sendSamplePushNotification sends a sample Notification to a default
	 * Topic and it in turn is delivered to all devices subscribed to that 
	 * Topic. 
	 */
	@Override
	public void sendSamplePushNotification(){
		PushNotificationRequestDTO request = getSamplePushNotificationRequest();
		Message message = getPreconfiguredMessageWithoutData(request);
		String response = sendAndGetResponse(message);
		logger.info("Sent message without data. Topic: " + request.getTopic() + ", " + response);
	}
	
	/**
	 * getSamplePushNotificationRequest generates a sample Push Notification
	 * Request Object using the data setup in configuration file.
	 * 
	 * @return
	 * 	<p>The desired PushNotificationRequestDTO instance.</p>
	 */
	 private PushNotificationRequestDTO getSamplePushNotificationRequest() {
	        PushNotificationRequestDTO request = new PushNotificationRequestDTO(defaults.get("title"),
	                defaults.get("message"),
	                defaults.get("topic"));
	        return request;
	  }

	/**
	 * getAndroidConfig returns the Android Configuration meta data
	 * for posting Notifications.
	 *   
	 * @param topic
	 * 	<p>An optional FCM topic.</p> 
	 * @return
	 * 	<p>The AndroidConfig instance.</p>
	 */
    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build()).build();
    }

    /**
	 * getApnsConfig returns the Apple Configuration meta data
	 * for posting Notifications.
	 *   
	 * @param topic
	 * 	<p>An optional FCM topic.</p> 
	 * @return
	 * 	<p>The ApnsConfig instance.</p>
	 */
    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    /**
     * getPreconfiguredMessageToToken constructs a message to be send to 
     * a device with a specific Token.
     * 
     * @param request
     * 	<p>PushNotificationRequestDTO instance having the Device Token.</p>
     * @return
     * 	<p>The Message instance encapsulating the actual Message.</p>
     */
    private Message getPreconfiguredMessageToToken(PushNotificationRequestDTO request) {
        return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
                .build();
    }

    /**
     * getPreconfiguredMessageWithoutData constructs a message to be send to 
     * a specific Topic.
     * 
     * @param request
     * 	<p>PushNotificationRequestDTO instance having the Topic name.</p>
     * @return
     * 	<p>The Message instance encapsulating the actual Message.</p>
     */
    private Message getPreconfiguredMessageWithoutData(PushNotificationRequestDTO request) {
        return getPreconfiguredMessageBuilder(request).setTopic(request.getTopic())
                .build();
    }

    /**
     * getPreconfiguredMessageWithData constructs a message to be send either to a topic
     * or a token.
     * 
     * @param data
     * 	<p>The payload data in key-value pair format.</p>
     * @param request
     * 	<p>PushNotificationRequestDTO instance having either the Topic name or Device Token.</p>
     * @return
     * 	<p>The Message instance encapsulating the actual Message.</p>
     */
    private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotificationRequestDTO request) {
    	if(request.getTopic() != null){
    		return getPreconfiguredMessageBuilder(request).putAllData(data).setTopic(request.getTopic())
                .build();
    	}
    	return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.getToken())
                .build();
    }

    /**
     * getPreconfiguredMessageBuilder generates a MessageBuilder instance using the
     * PushNotificationRequestDTO instance passed as an input.
     * 
     * @param request
     * 	<p>PushNotificationRequestDTO instance.</p>
     * @return
     * 	<p>The MessageBuilder instance.</p>
     */
    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequestDTO request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        new Notification(request.getTitle(), request.getMessage()));
    }
	
    /**
     * sendAndGetResponse sends the Push Notification message to the FCM cloud
     * and retrieves the acknowledgement.
     *  
     * @param message
     * 	<p>The Message instance encapsulating the Push Notification Meta Data.</p>
     * @return
     * 	<p>The Response message as text.</p>
     */
    private String sendAndGetResponse(Message message) {
        try {
			return FirebaseMessaging.getInstance().sendAsync(message).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
        return null;
    }

	
	/**
	 * initialize block is loaded at the time of initiation of this Service Bean
	 * via Spring framework. This block is responsible to load the Credential data
	 * of FCM and setup context for the Push Notification framework. 
	 */
	@PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
