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
public class RegistrationController {
	
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
				EmailDTO emailRequest = new EmailDTO(registrationRequest.getEmail(), "Registered successfully", "<h1>Registration Successfull.</h1><br><a href=\""
						+"http://3.128.4.163:8080/pubgroom-api/user-reg/verifiy-account/"+registrationRequest.getUserId()+"/"+otp+"\"> Click here to verify your account </a><br><br>Regards,<br>PUBG Rooms Team");
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
	
	@Operation(summary = "Verify user's account", description = "Activate user's account.", tags = { "UserRegistration" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = ModelAndView.class,hidden = true)))})
	@RequestMapping(value = "/verifiy-account/{userId}/{otp}", method=RequestMethod.GET)
	public ModelAndView verifyAccount(@PathVariable String userId, @PathVariable String otp) {
		logger.info("Entering RegistrationController.verifyAccount() method.");
		ModelAndView modelAndView = new ModelAndView("verify-account");
		modelAndView.addObject("userId", userId);
		userService.activateAccount(userId, otp);
		logger.info("Exiting RegistrationController.verifyAccount() method.");
		return modelAndView;
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
