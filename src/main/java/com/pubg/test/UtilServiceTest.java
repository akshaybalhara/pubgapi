package com.pubg.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pubg.ApplicationConfiguration;

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
			break;

		case 4:
			break;
			

		default: //All at once
			
			break;
		}		
		
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
