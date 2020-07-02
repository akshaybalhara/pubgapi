/**
 * 
 */
package com.pubg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pubg.dto.MatchesDTO;
import com.pubg.dto.StatusDTO;
import com.pubg.entity.MatchesEntity;
import com.pubg.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Anigam
 *
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
@Tag(name = "AdminController", description = "Admin Control Panel")
public class AdminController {
		
	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	AdminService adminService;
	@Operation(summary = "Validates the heart-beat of the API.", description = "Pings the API and check if its up and running.", tags = { "AdminController" })
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody String ping(){
		logger.info("Entering AdminController.ping() method.");
		return "User Matches API is up and running at end point /matches";
	}
	
	/**
	 * Register a new user.
	 */
	@Operation(summary = "Create a match to Join.", description = "Create a match to Join.", tags = { "AdminController" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = MatchesEntity.class,hidden = true)))})
	@RequestMapping(value = "/insertMatches", method=RequestMethod.POST)
	public  @ResponseBody StatusDTO insertMatches(@RequestBody MatchesDTO matchesDTO){
		logger.info("Entering AdminController.insertMatches() method.");
		StatusDTO response = null;
		matchesDTO.setOperation("Insert");
		response = adminService.processMatches(matchesDTO);
		logger.info("Exiting AdminController.insertMatches() method.");
		return 	response;
	}	
}
