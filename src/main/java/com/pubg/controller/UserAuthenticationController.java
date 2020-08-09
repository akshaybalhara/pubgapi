package com.pubg.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pubg.config.JwtTokenUtil;
import com.pubg.dto.DeviceTokenDTO;
import com.pubg.dto.EmailDTO;
import com.pubg.dto.StatusDTO;
import com.pubg.dto.UserDTO;
import com.pubg.entity.AppUpdateEntity;
import com.pubg.entity.UserEntity;
import com.pubg.exception.PUBGBusinessException;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.service.UserService;
import com.pubg.service.UtilService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * UserAuthenticationController is intended to maintain all API's related
 * to Login Component.
 * 
 * @author Prolifics.
 *
 */
@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/user-auth")
@Tag(name = "UserAuthentication", description = "User Authentication API")
public class UserAuthenticationController implements MessageConstants {
	
	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	/**
	 * UserService will be injected by Spring Framework.
	 */
	@Autowired
	private UserService userService;
	/**
	 * authenticationManager will be injected by Spring Framework.
	 */
	@Autowired
	private AuthenticationManager authenticationManager;
	/**
	 * jwtTokenUtil will be injected by Spring Framework.
	 */
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	/**
	 * UserService will be injected by Spring Framework.
	 */
	@Autowired
	private UtilService utilService;
	
	/**
	 * ping method is used to validate the connectivity
	 * of the API.
	 */
	@Operation(summary = "Validates the heart-beat of the API.", description = "Pings the API and check if its up and running.", tags = { "UserAuthentication" })
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody String ping(){
		logger.info("Entering UserAuthenticatioController.ping() method.");
		return "User Authentication API is up and running at end point /user-auth";
	}
	
	@Operation(summary = "Check app update.", description = "Check app update.", tags = { "UserAuthentication" })
	@RequestMapping(value="/checkUpdate", method=RequestMethod.GET)
	public @ResponseBody AppUpdateEntity checkUpdate(){
		logger.info("Entering UserAuthenticatioController.checkUpdate() method.");
		return userService.checkAppUpdate();
	}
	
	/**
	 * Checks the authenticity of the credentials provided by the user.
	 */
	@Operation(summary = "Verfiy the authenticity of the user.", description = "Check if the credentials provided by user are valid or not.", tags = { "UserAuthentication" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = UserEntity.class,hidden = true)))})
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public  @ResponseBody UserEntity processLogin(@RequestBody UserDTO authenticationRequest){
		logger.info("Entering UserAuthenticationController.processLogin() method.");
		UserEntity user = null;
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserId(), authenticationRequest.getPassword()));
			user = userService.getUserProfile(authenticationRequest.getUserId());
			if(!user.getStatus().equalsIgnoreCase(INACTIVE)) {
				user.setPassword(null);
				user.setToken(jwtTokenUtil.generateToken(user.getUserId()));	
				StatusDTO statusDto = new StatusDTO(true,MessageConstants.LOGIN_SUCCESS_CODE,"Logged in successfully");
				user.setStatusDto(statusDto);
			}else {
				throw new PUBGBusinessException("INA_001", "Your account is not activated yet. Please check your registered email to activate your account.");
			}	

		} catch (DisabledException e) {
			throw new PUBGBusinessException("USER_DISABLED", e.getMessage());
		} catch (BadCredentialsException e) {
			throw new PUBGBusinessException(MessageConstants.LOGIN_FAILURE_CODE,MessageConstants.LOGIN_FAILURE_MSG);
		}
		logger.info("Exiting UserAuthenticationController.processLogin() method.");
		return 	user;
	}
	
	/**
	 * processForgotPassword used to change the old password.
	 * 
	 * @param userName
	 * @return
	 */
	@Operation(summary = "Generate a link to reset password.", description = "User can generate a link and request to reset his/her password.", tags = { "UserAuthentication" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = String.class,hidden = true)))})
	@RequestMapping(value = "/forgotPassword/{userId}", method=RequestMethod.POST)
	public StatusDTO processForgotPassword(@PathVariable String userId) {
		logger.info("Entering UserAuthenticationController.processForgotPassword() method.");
		UserEntity user = userService.getUserProfile(userId);
		//String resetPasswordLink ="";
		StatusDTO status = null;
		if(user!=null && !user.getEmail().isEmpty()) {
			int pin = (int)(Math.random()*9000+1000);
			//resetPasswordLink = "http://3.128.4.163:8080/pubgroom-api/user-auth/resetPassword/"+userId+"/"+pin;
			EmailDTO emailRequest = new EmailDTO(user.getEmail(), "Reset password of your account", RESET_PASSWORD_TEMPLATE_BEGIN+userId+"/"+pin+RESET_PASSWORD_TEMPLATE_END);
			emailRequest.setUserId(userId);
			emailRequest.setData(Integer.toString(pin));
			utilService.sendEmailWithAttachment(emailRequest,"ResetPassword");
			status = new StatusDTO(true, "RST_001", "Successfully sent password reset link to your registered email.");
		}
		logger.info("Exiting UserAuthenticationController.processForgotPassword() method.");
		return status;
	}
	
	/**
	 * processForgotPassword used to change the old password.
	 * 
	 * @param userName
	 * @return
	 */
	@Operation(summary = "Reset forgot password", description = "User can reset his/her forgotten password using this service.", tags = { "UserAuthentication" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = ModelAndView.class,hidden = true)))})
	@RequestMapping(value = "/resetPassword/{userId}/{pin}", method=RequestMethod.GET)
	public ModelAndView processResetPassword(@PathVariable String userId, @PathVariable String pin) {
		logger.info("Entering UserAuthenticationController.processResetPassword() method.");
		ModelAndView modelAndView = new ModelAndView("forgotPassword");
		modelAndView.addObject("userId", userId);
		modelAndView.addObject("pin", pin);
		logger.info("Exiting UserAuthenticationController.processResetPassword() method.");
		return modelAndView;
	}
	
	/**
	 * processForgotPassword used to change the old password.
	 * 
	 * @param userName
	 * @return
	 */
	@Operation(summary = "Update password.", description = "Update password.", tags = { "UserAuthentication" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = String.class,hidden = true)))})
	@RequestMapping(value = "/updatePassword", produces = { MediaType.APPLICATION_JSON_VALUE }, method=RequestMethod.POST)
	public @ResponseBody StatusDTO updatePassword(@RequestBody String passResetReq) {
		logger.info("Entering UserAuthenticationController.updatePassword() method.");
		StatusDTO status = null;
		try {
			System.out.println(passResetReq);
			Map<String, String> businessDataMap = formatDataInKeyValuePairs(passResetReq);
			UserEntity user = userService.getUserProfile(businessDataMap.get("userId"));
			if(user!=null && user.getResetPin().equals(businessDataMap.get("pin")) && user.getUserId().equals(businessDataMap.get("userId"))) {
				status = userService.processForgotPassword(user.getUserId(), businessDataMap.get("newPassword"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		logger.info("Exiting UserAuthenticationController.updatePassword() method.");
		return status;
	}
	
	/**
	 * Updates user's device token for push notifications.
	 */
	@Operation(summary = "Updates user's device token for push notifications.", description = "Updates user's device token for push notifications.", tags = { "UserAuthentication" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = StatusDTO.class,hidden = true)))})
	@RequestMapping(value = "/updateDeviceToken", method=RequestMethod.POST)
	public  @ResponseBody StatusDTO updateDeviceToken(@RequestBody DeviceTokenDTO deviceTokenDto){
		logger.info("Entering UserAuthenticationController.updateDeviceToken() method.");
		StatusDTO statusDTO = userService.updateDeviceToken(deviceTokenDto);
		logger.info("Exiting UserAuthenticationController.updateDeviceToken() method.");
		return 	statusDTO;
	}
	
	public static Map<String, String> formatDataInKeyValuePairs(String serializedData) throws UnsupportedEncodingException {
	    Map<String, String> data_pairs = new LinkedHashMap<String, String>();
	    String[] pairs = serializedData.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        data_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return data_pairs;
	}
	
	
}
