package com.pubg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pubg.dto.DeviceTokenDTO;
import com.pubg.dto.EmailDTO;
import com.pubg.dto.StatusDTO;
import com.pubg.entity.RegistrationEntity;
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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user-reg")
@Tag(name = "UserRegistration", description = "To register a new user")
public class RegistrationController implements MessageConstants {
	
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
	 * UserService will be injected by Spring Framework.
	 */
	@Autowired
	private UtilService utilService;
	
	/**
	 * ping method is used to validate the connectivity
	 * of the API.
	 */
	@Operation(summary = "Validates the heart-beat of the API.", description = "Pings the API and check if its up and running.", tags = { "UserRegistration" })
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody String ping(){
		logger.info("Entering RegistrationController.ping() method.");
		return "User Registration API is up and running at end point /user-auth";
	}
	
	/**
	 * Register a new user.
	 */
	@Operation(summary = "Register a new user.", description = "Register a new user.", tags = { "UserRegistration" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = RegistrationEntity.class,hidden = true)))})
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	public  @ResponseBody StatusDTO register(@RequestBody RegistrationEntity registrationRequest){
		logger.info("Entering RegistrationController.register() method.");
		StatusDTO statusDto = new StatusDTO();
		try {
			String otp = Integer.toString((int)(Math.random()*9000)+1000);
			statusDto = userService.registerNewUser(registrationRequest,otp);
			if(statusDto.isSucceeded()) {
				EmailDTO emailRequest = new EmailDTO(registrationRequest.getEmail(), "Registered successfully", ACTIVATE_ACCOUNT_TEMPLATE_BEGIN+registrationRequest.getUserId()+"/"+otp+ACTIVATE_ACCOUNT_TEMPLATE_END);
				emailRequest.setUserId(registrationRequest.getUserId());
				utilService.sendEmailWithAttachment(emailRequest,"Registration");
			}
		} catch (DisabledException e) {
			throw new PUBGBusinessException("USER_DISABLED", e.getMessage());
		} catch (BadCredentialsException e) {
			throw new PUBGBusinessException(MessageConstants.SOMETHING_WENT_WRONG,MessageConstants.SOMETHING_WENT_WRONG_MSG);
		}
		logger.info("Exiting RegistrationController.register() method.");
		return 	statusDto;
	}
	
	/**
	 * Update an existing user.
	 */
	@Operation(summary = "Update an existing user", description = "Update an existing user", tags = { "UserRegistration" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = RegistrationEntity.class,hidden = true)))})
	@RequestMapping(value = "/updateUser", method=RequestMethod.POST)
	public  @ResponseBody StatusDTO updateUser(@RequestBody RegistrationEntity registrationRequest){
		logger.info("Entering RegistrationController.updateUser() method.");
		StatusDTO statusDto = new StatusDTO();
		try {
			System.out.println(registrationRequest.toString());
			statusDto = userService.updateExistingUser(registrationRequest,null,null);
		} catch (DisabledException e) {
			throw new PUBGBusinessException("USER_DISABLED", e.getMessage());
		} catch (BadCredentialsException e) {
			throw new PUBGBusinessException(MessageConstants.SOMETHING_WENT_WRONG,MessageConstants.SOMETHING_WENT_WRONG_MSG);
		}
		logger.info("Exiting RegistrationController.updateUser() method.");
		return 	statusDto;
	}
	
	/**
	 * Update pubgUsername.
	 */
	@Operation(summary = "Update an existing user", description = "Update an existing user", tags = { "UserRegistration" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = RegistrationEntity.class,hidden = true)))})
	@RequestMapping(value = "/updatePubgUsername/{userId}/{pubgUsername}", method=RequestMethod.POST)
	public  @ResponseBody StatusDTO updatePubgUsername(@PathVariable String userId, @PathVariable String pubgUsername){
		logger.info("Entering RegistrationController.updatePubgUsername() method.");
		StatusDTO statusDto = new StatusDTO();
		try {
			statusDto = userService.updateExistingUser(null,userId,pubgUsername);
		} catch (DisabledException e) {
			throw new PUBGBusinessException("USER_DISABLED", e.getMessage());
		} catch (BadCredentialsException e) {
			throw new PUBGBusinessException(MessageConstants.SOMETHING_WENT_WRONG,MessageConstants.SOMETHING_WENT_WRONG_MSG);
		}
		logger.info("Exiting RegistrationController.updatePubgUsername() method.");
		return 	statusDto;
	}
	
	@Operation(summary = "Verify user's account", description = "Activate user's account.", tags = { "UserRegistration" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = ModelAndView.class,hidden = true)))})
	@RequestMapping(value = "/verify-account/{userId}/{otp}", method=RequestMethod.GET)
	public ModelAndView verifyAccount(@PathVariable String userId, @PathVariable String otp) {
		logger.info("Entering RegistrationController.verifyAccount() method.");
		ModelAndView modelAndView = new ModelAndView("verify-account");
		modelAndView.addObject("userId", userId);
		userService.activateAccount(userId, otp);
		logger.info("Exiting RegistrationController.verifyAccount() method.");
		return modelAndView;
	}
	
	@Operation(summary = "Validates if email exists.", description = "Validates if email exists.", tags = { "UserRegistration" })
	@RequestMapping(value = "/verify-email", method=RequestMethod.GET)
	public @ResponseBody StatusDTO verifyEmail(@RequestParam String email){
		logger.info("Entering RegistrationController.verifyEmail() method.");
		boolean isEmailExists = userService.checkEmail(email);
		StatusDTO status = null;
		if(isEmailExists) {
			status = new StatusDTO(true, "EML_001", "Email already exists.");
		}else {
			status = new StatusDTO(false, "EML_002", "Email is valid.");
		}
		return status;
	}
	
	@Operation(summary = "Validates if phone exists.", description = "Validates if phone exists.", tags = { "UserRegistration" })
	@RequestMapping(value = "/verify-phone/{phone}", method=RequestMethod.GET)
	public @ResponseBody StatusDTO verifyPhone(@PathVariable String phone){
		logger.info("Entering RegistrationController.verifyPhone() method.");
		boolean isPhoneExists = userService.checkPhone(phone);
		StatusDTO status = null;
		if(isPhoneExists) {
			status = new StatusDTO(true, "PHN_001", "Phone already exists.");
		}else {
			status = new StatusDTO(false, "PHN_002", "Phone is valid.");
		}
		return status;
	}
	
	@Operation(summary = "Validates if userId exists.", description = "Validates if userId exists.", tags = { "UserRegistration" })
	@RequestMapping(value = "/verify-userid/{userId}", method=RequestMethod.GET)
	public @ResponseBody StatusDTO verifyUserId(@PathVariable String userId){
		logger.info("Entering RegistrationController.verifyUserId() method.");
		boolean isUserIdExists = userService.checkUserId(userId);
		StatusDTO status = null;
		if(isUserIdExists) {
			status = new StatusDTO(true, "USR_001", "UserId already exists.");
		}else {
			status = new StatusDTO(false, "USR_002", "UserId is valid.");
		}
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
	
	
}
