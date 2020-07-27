/**
 * 
 */
package com.pubg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pubg.dto.MatchesDTO;
import com.pubg.dto.PushNotificationRequestDTO;
import com.pubg.dto.StatusDTO;
import com.pubg.dto.WalletDTO;
import com.pubg.entity.MatchesEntity;
import com.pubg.entity.WalletEntity;
import com.pubg.service.AdminService;
import com.pubg.service.PushNotificationService;

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
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private PushNotificationService pushNotificationService;
	
	
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
		/*PushNotificationRequestDTO request = new PushNotificationRequestDTO("New match available.", "Join to win Rs."+matchesDTO.getFirstPrize(), "all");
		pushNotificationService.sendPushNotificationToTopic(request);*/
		logger.info("Exiting AdminController.insertMatches() method.");
		return 	response;
	}	
	
	/**
	 * Deduct balance from wallet.
	 */
	@Operation(summary = "Deduct balance from wallet", description = "Deduct balance from wallet", tags = { "AdminController" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = StatusDTO.class,hidden = true)))})
	@RequestMapping(value = "/deductWalletBalance", method=RequestMethod.POST)
	public  @ResponseBody StatusDTO deductWalletBalance(@RequestBody WalletDTO walletDTO){
		logger.info("Entering AdminController.deductWalletBalance() method.");
		StatusDTO response = null;
		walletDTO.setOperation("Deduction");
		response = adminService.updateWallet(walletDTO);
		logger.info("Exiting AdminController.deductWalletBalance() method.");
		return 	response;
	}
	
	/**
	 * Add balance to wallet.
	 */
	@Operation(summary = "Add balance to wallet", description = "Add balance to wallet", tags = { "AdminController" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = StatusDTO.class,hidden = true)))})
	@RequestMapping(value = "/addWalletBalance", method=RequestMethod.POST)
	public  @ResponseBody StatusDTO addWalletBalance(@RequestBody WalletDTO walletDTO){
		logger.info("Entering AdminController.addWalletBalance() method.");
		StatusDTO response = null;
		walletDTO.setOperation("Addition");
		response = adminService.updateWallet(walletDTO);
		logger.info("Exiting AdminController.addWalletBalance() method.");
		return 	response;
	}
	
	/**
	 * Get wallet balance.
	 */
	@Operation(summary = "Get wallet balance", description = "Get wallet balance", tags = { "AdminController" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = WalletEntity.class,hidden = true)))})
	@RequestMapping(value = "/getWalletBalance/{userId}", method=RequestMethod.GET)
	public  @ResponseBody WalletEntity getWalletBalance(@PathVariable String userId){
		logger.info("Entering AdminController.getWalletBalance() method.");
		WalletEntity entity = adminService.getWalletBalance(userId);
		logger.info("Exiting AdminController.getWalletBalance() method.");
		return 	entity;
	}
}
