/**
 * 
 */
package com.pubg.controller;

import java.util.List;
import java.util.TreeMap;

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
import com.pubg.dto.StatusDTO;
import com.pubg.dto.WalletDTO;
import com.pubg.entity.MatchesEntity;
import com.pubg.entity.PaymentEntity;
import com.pubg.entity.UserEntity;
import com.pubg.entity.WalletEntity;
import com.pubg.service.AdminService;
import com.pubg.service.PushNotificationService;
import com.pubg.service.UserService;

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
	private UserService userService;

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
		UserEntity entity = userService.getUserProfileByPubgUsername(walletDTO.getUserId());
		walletDTO.setUserId(entity.getUserId());
		walletDTO.setUpdatingWinningAmount(true);
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
		UserEntity entity = userService.getUserProfileByPubgUsername(walletDTO.getUserId());
		walletDTO.setUserId(entity.getUserId());
		walletDTO.setUpdatingWinningAmount(true);
		response = adminService.updateWallet(walletDTO);
		logger.info("Exiting AdminController.addWalletBalance() method.");
		return 	response;
	}
	
	/**
	 * Update roomId and Password of a match.
	 */
	@Operation(summary = "Update roomId and Password of a match", description = "Update roomId and Password of a match", tags = { "AdminController" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = StatusDTO.class,hidden = true)))})
	@RequestMapping(value = "/updateRoomIdPass", method=RequestMethod.POST)
	public  @ResponseBody StatusDTO updateRoomIdPass(@RequestBody TreeMap<String, String> roomIdPassReq){
		logger.info("Entering AdminController.updateRoomIdPass() method.");
		StatusDTO statusDto = adminService.updateRoomIdPass(roomIdPassReq.get("roomId"),roomIdPassReq.get("password"), roomIdPassReq.get("matchId"));
		logger.info("Exiting AdminController.updateRoomIdPass() method.");
		return 	statusDto;
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
	
	/**
	 * Cancel or Expires a match.
	 */
	@Operation(summary = "Cancel or Expires a match.", description = "Cancel or Expires a match.", tags = { "AdminController" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = StatusDTO.class,hidden = true)))})
	@RequestMapping(value = "/expireMatch/{matchId}/{matchStatus}", method=RequestMethod.POST)
	public  @ResponseBody StatusDTO expireAMatch(@PathVariable String matchId, @PathVariable String matchStatus){
		logger.info("Entering AdminController.expireAMatch() method.");
		StatusDTO statusDto = adminService.expireAMatch(matchId,matchStatus);
		logger.info("Exiting AdminController.expireAMatch() method.");
		return 	statusDto;
	}
	
	/**
	 * Update Prizes.
	 */
	@Operation(summary = "Update Prizes.", description = "Update Prizes.", tags = { "AdminController" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = StatusDTO.class,hidden = true)))})
	@RequestMapping(value = "/updatePrizes", method=RequestMethod.POST)
	public  @ResponseBody StatusDTO updatePrizes(@RequestBody TreeMap<String, String> updatePrizesReq){
		logger.info("Entering AdminController.updatePrizes() method.");
		StatusDTO statusDto = adminService.updatePrizes(updatePrizesReq);
		logger.info("Exiting AdminController.updatePrizes() method.");
		return 	statusDto;
	}
	
	/**
	 * Get all withdraw requests.
	 */
	@Operation(summary = "Get all withdraw requests.", description = "Get all withdraw requests.", tags = { "AdminController" })
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK",
	                content = @Content(schema = @Schema(implementation = StatusDTO.class,hidden = true)))})
	@RequestMapping(value = "/withdrawRequests", method=RequestMethod.GET)
	public  @ResponseBody List<PaymentEntity> getWithdrawRequests(){
		logger.info("Entering AdminController.withdrawRequests() method.");
		List<PaymentEntity> withdrawlRequests = adminService.getWithdrawRequests();
		logger.info("Exiting AdminController.withdrawRequests() method.");
		return 	withdrawlRequests;
	}
}
