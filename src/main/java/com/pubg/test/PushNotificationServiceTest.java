package com.pubg.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.pubg.ApplicationConfiguration;
import com.pubg.dto.PushNotificationRequestDTO;
import com.pubg.service.PushNotificationService;

/**
 * 
 * Test Class to test Util Service.
 */
public class PushNotificationServiceTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ApplicationConfiguration.class);
		ctx.refresh();
		int choice = 2;
		
		switch (choice) {
		case 1:
			testSendPushNotificationToToken(ctx);
			break;
		case 2:
			testSendPushNotificationToTokenWithData(ctx);
			break;
		default:			
			break;
		}		
		
	}


	private static void testSendPushNotificationToToken(AnnotationConfigApplicationContext ctx) {
		PushNotificationService service = ctx.getBean(PushNotificationService.class);
		PushNotificationRequestDTO request = new PushNotificationRequestDTO("Leave Rejected", "Your Leave requested is Rejected. Please get back to work.", null);
		//request.setToken("dncMuzPK0S4:APA91bH6Ia3jIZe3i8kZ-Fg_Osxwc8B0pl7zJDlXmODKU1QvTdxqoGMia-ph2caKKOLOgWdAq0o48qWiqMZ0OtFV1TOJzuYbFPAIuzHw5GUQ2FzdrA-Y3rt-_3chelyZOnawc1qzn7Pv");
		request.setToken("cp3kWDQefxY:APA91bFH8wRIBw4ORs2AOMZCAR41-c1Azpa1BH9JnGwNa15sjn8AzXu9Ct-6zATXTfuVOL8BUcCg7z-HXeIbrFmmyIpvZUsODcUzi79KL3vp3USmG7_OMVL0N3UsJ9Kuy8xkaeTFROff");
		service.sendPushNotificationToToken(request);
	}
	
	private static void testSendPushNotificationToTokenWithData(AnnotationConfigApplicationContext ctx) {
		PushNotificationService service = ctx.getBean(PushNotificationService.class);
		PushNotificationRequestDTO request = new PushNotificationRequestDTO("Leave Accepted", "Your Leave requested is Accepted.", null);
		request.setToken("dncMuzPK0S4:APA91bH6Ia3jIZe3i8kZ-Fg_Osxwc8B0pl7zJDlXmODKU1QvTdxqoGMia-ph2caKKOLOgWdAq0o48qWiqMZ0OtFV1TOJzuYbFPAIuzHw5GUQ2FzdrA-Y3rt-_3chelyZOnawc1qzn7Pv");
		//request.setToken("cp3kWDQefxY:APA91bFH8wRIBw4ORs2AOMZCAR41-c1Azpa1BH9JnGwNa15sjn8AzXu9Ct-6zATXTfuVOL8BUcCg7z-HXeIbrFmmyIpvZUsODcUzi79KL3vp3USmG7_OMVL0N3UsJ9Kuy8xkaeTFROff");
		Map<String, String> data = new HashMap<>();
		data.put("landing_page", "second");
		data.put("price", "43000");
		service.sendPushNotificationToTokenWithData(request,data);
	}
	
}
