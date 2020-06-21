package com.pubg.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pubg.entity.MatchesEntity;
import com.pubg.exception.PUBGBusinessException;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.service.MatchesService;
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
@RequestMapping("/matches")
@Tag(name = "Matches", description = "List Matches")
public class MatchesController {
	
	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	/**
	 * UserService will be injected by Spring Framework.
	 */
	@Autowired
	private MatchesService matchesService;
	
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
		logger.info("Entering MatchesController.ping() method.");
		return "User Matches API is up and running at end point /matches";
	}
	
	/**
	 * Register a new user.
	 */
	@Operation(summary = "Get list of all matches.", description = "Get list of all matches.", tags = { "Matches" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = MatchesEntity.class,hidden = true)))})
	@RequestMapping(value = "/list-matches", method=RequestMethod.GET)
	public  @ResponseBody List<MatchesEntity> getAllMatches(){
		logger.info("Entering MatchesController.getAllMatches() method.");
		List<MatchesEntity> matches = new ArrayList<MatchesEntity>();
		try {
			matches = matchesService.listAllMatches();
		} catch (DisabledException e) {
			throw new PUBGBusinessException("USER_DISABLED", e.getMessage());
		} catch (BadCredentialsException e) {
			throw new PUBGBusinessException(MessageConstants.SOMETHING_WENT_WRONG,MessageConstants.SOMETHING_WENT_WRONG_MSG);
		}
		logger.info("Exiting MatchesController.getAllMatches() method.");
		return 	matches;
	}	
	
}
