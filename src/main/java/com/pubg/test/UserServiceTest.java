package com.pubg.test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pubg.ApplicationConfiguration;
import com.pubg.dto.ChangePasswordDTO;
import com.pubg.dto.DeviceTokenDTO;
import com.pubg.dto.StatusDTO;
import com.pubg.entity.UserEntity;
import com.pubg.service.UserService;

/**
 * 
 * Test Class to test User Service.
 */
public class UserServiceTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ApplicationConfiguration.class);
		ctx.refresh();
		
		//testAuthentication(ctx);	
		//testLookupService(ctx);
		//testLeaveSummary(ctx);
		//testLeaveAppData(ctx);
		//testApplyLeave(ctx);
		//testRecommendLeave(ctx);
		//testApproveLeave(ctx);
		//testAcceptLeave(ctx);
		//testRejectLeave(ctx);
		//testCancelLeave(ctx);
		//testGetLeaveDetails(ctx);
		//testGetAllApprovers(ctx);
		//testupdateUserProfile(ctx);
		//testChangePassword(ctx);
		//testGetEmployeeDetails(ctx);
		//testUpdateDeviceToken(ctx);
		
		/*UserServiceTest userServiceTest = new UserServiceTest();
		String fromDate="2020-03-17";  
		String toDate="2020-03-21";  
		try {
			Date fromD=new SimpleDateFormat("yyyy-mm-dd").parse(fromDate);
			Date toD=new SimpleDateFormat("yyyy-mm-dd").parse(toDate);
			System.out.println(userServiceTest.calculateNoOfDays(fromD,toD));
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		
		
		
	}
	
	private static void testGetEmployeeDetails(AnnotationConfigApplicationContext ctx) {
		UserService userService = ctx.getBean(UserService.class); 
		UserEntity entity = userService.getUserProfile("1111");
		doJsonize(entity);
		
	}

	private static void testChangePassword(AnnotationConfigApplicationContext ctx) {
		UserService userService = ctx.getBean(UserService.class); 
		ChangePasswordDTO changePass = new ChangePasswordDTO();
		changePass.setEmployeeId("1111");
		//changePass.setOldPassword("1111");
		changePass.setNewPassword("2222");
		StatusDTO status = userService.processForgotPassword(changePass.getEmployeeId(), changePass.getNewPassword());
		doJsonize(status);
		
	}
	
	private static void testUpdateDeviceToken(AnnotationConfigApplicationContext ctx) {
		UserService userService = ctx.getBean(UserService.class); 
		DeviceTokenDTO deviceToken = new DeviceTokenDTO();
		deviceToken.setUserId("1111");
		deviceToken.setDeviceType("Android");
		deviceToken.setDevicetoken("jhksdksdchlksjkjjgvhsxknhbcsd=");
		StatusDTO status = userService.updateDeviceToken(deviceToken);
		doJsonize(status);
		
	}

	private int calculateNoOfDays(Date fromDate, Date toDate) {
		LocalDate fromLocal = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate toLocal = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int days = (int)ChronoUnit.DAYS.between(fromLocal, toLocal);
		days = days +1;
		return days;
	}

	private static void testupdateUserProfile(AnnotationConfigApplicationContext ctx) {
		UserService userService = ctx.getBean(UserService.class); 
		UserEntity entity = new UserEntity();
		entity.setUserId("akshay_balhara");
		entity.setPhone("9991306894");
		StatusDTO status = userService.updateUserProfile(entity);
		doJsonize(status);
	}
	
	
	
	private static Date getNextDate(Date curDate) {
		Date nextDate = null;
		try {
            Calendar today = Calendar.getInstance();
            today.setTime(curDate);
            today.add(Calendar.DAY_OF_YEAR, 1);
            nextDate = today.getTime();
        } catch (Exception e) {
            return nextDate;
        }
		return nextDate;
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
