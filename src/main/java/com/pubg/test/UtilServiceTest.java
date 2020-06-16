package com.pubg.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pubg.ApplicationConfiguration;
import com.pubg.service.UtilService;

/**
 * 
 * Test Class to test Util Service.
 */
public class UtilServiceTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ApplicationConfiguration.class);
		ctx.refresh();
		int choice = 3;
		
		switch (choice) {
		case 1:
			break;

		case 2:
			break;

		case 3:
			testGetAllRemindersOfUser(ctx);
			break;

		case 4:
			testGetAllNotificationsOfUser(ctx);
			break;
			

		default: //All at once
			testGetAllRemindersOfUser(ctx);
			testGetAllNotificationsOfUser(ctx);
			break;
		}		
		
	}
	
	private static void testGetAllRemindersOfUser(AnnotationConfigApplicationContext ctx) {
		UtilService utilService = ctx.getBean(UtilService.class);		
		doJsonize(utilService.getAllRemindersOfUser("2222"));
	}
	
	private static void testGetAllNotificationsOfUser(AnnotationConfigApplicationContext ctx) {
		UtilService utilService = ctx.getBean(UtilService.class);		
		doJsonize(utilService.getAllNotificationsOfUser("2222"));
	}
	
	private static void doJsonize(Object object){
		if(object == null) return;
		try{
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			System.out.println(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
