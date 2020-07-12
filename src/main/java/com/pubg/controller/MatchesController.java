package com.pubg.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.pubg.dto.StatusDTO;
import com.pubg.entity.JoinedMatchesEntity;
import com.pubg.entity.MatchesEntity;
import com.pubg.exception.PUBGBusinessException;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.service.MatchesService;

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
	 * Get list of all matches.
	 */
	@Operation(summary = "Get list of all matches.", description = "Get list of all matches.", tags = { "Matches" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = MatchesEntity.class,hidden = true)))})
	@RequestMapping(value = "/list-matches/{leagueType}", method=RequestMethod.GET)
	public  @ResponseBody List<MatchesEntity> getAllMatches(@PathVariable String leagueType){
		logger.info("Entering MatchesController.getAllMatches() method.");
		List<MatchesEntity> matches = new ArrayList<MatchesEntity>();
		try {
			matches = matchesService.listAllMatches(leagueType);
		} catch (DisabledException e) {
			throw new PUBGBusinessException("USER_DISABLED", e.getMessage());
		} catch (BadCredentialsException e) {
			throw new PUBGBusinessException(MessageConstants.SOMETHING_WENT_WRONG,MessageConstants.SOMETHING_WENT_WRONG_MSG);
		}
		logger.info("Exiting MatchesController.getAllMatches() method.");
		return 	matches;
	}	
	
	/**
	 * Get match details.
	 */
	@Operation(summary = "Get match details.", description = "Get match details.", tags = { "Matches" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = MatchesEntity.class,hidden = true)))})
	@RequestMapping(value = "/match-details/{matchId}", method=RequestMethod.GET)
	public  @ResponseBody MatchesEntity getMatchDetails(@PathVariable String matchId){
		logger.info("Entering MatchesController.getMatchDetails() method.");
		MatchesEntity match = new MatchesEntity();
		try {
			match = matchesService.getMatchById(matchId);
		} catch (DisabledException e) {
			throw new PUBGBusinessException("USER_DISABLED", e.getMessage());
		} catch (BadCredentialsException e) {
			throw new PUBGBusinessException(MessageConstants.SOMETHING_WENT_WRONG,MessageConstants.SOMETHING_WENT_WRONG_MSG);
		}
		logger.info("Exiting MatchesController.getMatchDetails() method.");
		return 	match;
	}
	
	/**
	 * Get participants list.
	 */
	@Operation(summary = "Get participants list.", description = "Get participants list.", tags = { "Matches" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = List.class,hidden = true)))})
	@RequestMapping(value = "/participants/{matchId}", method=RequestMethod.GET)
	public  @ResponseBody List<String> getParticipants(@PathVariable String matchId){
		logger.info("Entering MatchesController.getParticipants() method.");
		List<String> participantsList = new ArrayList<String>();
		try {
			participantsList = matchesService.getParticipants(matchId);
		} catch (DisabledException e) {
			throw new PUBGBusinessException("USER_DISABLED", e.getMessage());
		} catch (BadCredentialsException e) {
			throw new PUBGBusinessException(MessageConstants.SOMETHING_WENT_WRONG,MessageConstants.SOMETHING_WENT_WRONG_MSG);
		}
		logger.info("Exiting MatchesController.getParticipants() method.");
		return 	participantsList;
	}
	
	/**
	 * Join a match.
	 */
	@Operation(summary = "Join a match", description = "Join a match.", tags = { "Matches" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = JoinedMatchesEntity.class,hidden = true)))})
	@RequestMapping(value = "/join-match", method=RequestMethod.POST)
	public  @ResponseBody StatusDTO joinMatch(@RequestBody JoinedMatchesEntity request){
		logger.info("Entering MatchesController.joinMatch() method.");
		StatusDTO statusDTO = new StatusDTO();
		try {
			statusDTO = matchesService.joinAMatch(request);
		} catch (DisabledException e) {
			throw new PUBGBusinessException("USER_DISABLED", e.getMessage());
		} catch (BadCredentialsException e) {
			throw new PUBGBusinessException(MessageConstants.SOMETHING_WENT_WRONG,MessageConstants.SOMETHING_WENT_WRONG_MSG);
		}
		logger.info("Exiting MatchesController.joinMatch() method.");
		return 	statusDTO;
	}
	
}
