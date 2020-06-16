package com.pubg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.pubg.dto.StatusDTO;
import com.pubg.dto.UserDTO;
import com.pubg.entity.UserEntity;
import com.pubg.exception.PUBGBusinessException;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.service.UserService;

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
@RequestMapping("/user-auth")
@Tag(name = "UserAuthentication", description = "User Authentication API")
public class UserAuthenticationController {
	
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
	 * ping method is used to validate the connectivity
	 * of the API.
	 */
	@Operation(summary = "Validates the heart-beat of the API.", description = "Pings the API and check if its up and running.", tags = { "UserAuthentication" })
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody String ping(){
		logger.info("Entering UserAuthenticatioController.ping() method.");
		return "User Authentication API is up and running at end point /user-auth";
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
			user.setPassword(null);
			user.setToken(jwtTokenUtil.generateToken(user.getUserId()));	
			StatusDTO statusDto = new StatusDTO(true,MessageConstants.LOGIN_SUCCESS_CODE,"Logged in successfully");
			user.setStatusDto(statusDto);

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
	@Operation(summary = "Reset forgot password", description = "User can reset his/her forgotten password using this service.", tags = { "UserAuthentication" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = ModelAndView.class,hidden = true)))})
	@RequestMapping(value = "/resetPassword/{employeeId}", method=RequestMethod.GET)
	public ModelAndView processResetPassword(@PathVariable String employeeId) {
		logger.info("Entering UserAuthenticationController.processResetPassword() method.");
		ModelAndView modelAndView = new ModelAndView("forgotPassword");
		modelAndView.addObject("employeeId", employeeId);
		String pin = ""+((int)(Math.random()*9000)+1000);
		modelAndView.addObject("pin", pin);
		StatusDTO status = userService.processForgotPassword(employeeId, pin);
		modelAndView.addObject("status", status.getStatusMessage());
		logger.info("Exiting UserAuthenticationController.processResetPassword() method.");
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
