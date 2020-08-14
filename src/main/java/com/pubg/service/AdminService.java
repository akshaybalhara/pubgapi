/**
 * 
 */
package com.pubg.service;

import java.util.List;
import java.util.TreeMap;

import com.pubg.dto.MatchesDTO;
import com.pubg.dto.StatusDTO;
import com.pubg.dto.WalletDTO;
import com.pubg.entity.PaymentEntity;
import com.pubg.entity.WalletEntity;

/**
 * @author Anigam
 *
 */
public interface AdminService {
		
	public StatusDTO processMatches(MatchesDTO matchesDTO);

	public StatusDTO updateWallet(WalletDTO walletDTO);

	public WalletEntity getWalletBalance(String userId);

	public StatusDTO updateRoomIdPass(String roomId, String password, String matchId);

	public StatusDTO expireAMatch(String matchId, String matchStatus);

	public StatusDTO updatePrizes(TreeMap<String, String> updatePrizesReq);

	public List<PaymentEntity> getWithdrawRequests();
	
}
