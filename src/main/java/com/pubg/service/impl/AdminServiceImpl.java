/**
 * 
 */
package com.pubg.service.impl;

import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubg.dto.MatchesDTO;
import com.pubg.dto.StatusDTO;
import com.pubg.dto.WalletDTO;
import com.pubg.entity.PaymentEntity;
import com.pubg.entity.WalletEntity;
import com.pubg.exception.PUBGBusinessException;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.repository.AdminRepository;
import com.pubg.service.AdminService;
import com.pubg.service.BaseService;

/**
 * @author Anigam
 *
 */
@Service
public class AdminServiceImpl extends BaseService implements AdminService,MessageConstants{

	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public StatusDTO processMatches(MatchesDTO matchesDTO) {
		logger.info("Entering AdminServiceImpl.processMatches() method.");
		StatusDTO status = null;
		if(matchesDTO!=null && matchesDTO.getOperation()!=null){
			String operation = matchesDTO.getOperation();
			switch (operation) {
			case "Insert":
				adminRepository.insertMatch(matchesDTO);
				status = new StatusDTO(true,ADMIN_INSERT_MATCH_CODE,ADMIN_INSERT_MATCH_MSG);
				break;
			case "Update":
				adminRepository.updateMatch(matchesDTO);
				status = new StatusDTO(true,"AIM_002","Match updated successfully.");
				break;
			default:
				throw new PUBGBusinessException(SOMETHING_WENT_WRONG, SOMETHING_WENT_WRONG_MSG);
			}			
		}
		logger.info("Exiting AdminServiceImpl.processMatches() method.");
		return status;
	}

	@Override
	public StatusDTO updateWallet(WalletDTO walletDTO) {
		logger.info("Entering AdminServiceImpl.updateWallet().");
		StatusDTO status = null;
		adminRepository.updateBalance(walletDTO);
		status = new StatusDTO(true,"WAL_001","Balance updated successfully.");
		logger.info("Exiting AdminServiceImpl.updateWallet().");
		return status;
	}

	@Override
	public WalletEntity getWalletBalance(String userId) {
		logger.info("Entering AdminServiceImpl.getWalletBalance().");
		WalletEntity entity = adminRepository.getBalance(userId);
		logger.info("Entering AdminServiceImpl.getWalletBalance().");
		return entity;
	}

	@Override
	public StatusDTO updateRoomIdPass(String roomId, String password, String matchId) {
		adminRepository.updateRoomIdPass(roomId,password,matchId);
		StatusDTO status = new StatusDTO(true,"RMP_001","RoomId and Password updated successfully.");
		return status;
	}

	@Override
	public StatusDTO expireAMatch(String matchId, String matchStatus) {
		adminRepository.expireAMatch(matchId,matchStatus);
		StatusDTO status = new StatusDTO(true,"EXP_001","Status of match updated successfully");
		return status;
	}

	@Override
	public StatusDTO updatePrizes(TreeMap<String, String> updatePrizesReq) {
		adminRepository.updatePrizes(updatePrizesReq);
		StatusDTO status = new StatusDTO(true,"EXP_001","Prizes updated successfully");
		return status;
	}

	@Override
	public List<PaymentEntity> getWithdrawRequests() {
		List<PaymentEntity> withdrawlReqs = adminRepository.getWithdrawRequests();
		return withdrawlReqs;
	}
	
}
