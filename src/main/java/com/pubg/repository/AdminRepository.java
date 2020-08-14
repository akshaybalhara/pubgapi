/**
 * 
 */
package com.pubg.repository;

import java.util.List;
import java.util.TreeMap;

import com.pubg.dto.MatchesDTO;
import com.pubg.dto.WalletDTO;
import com.pubg.entity.PaymentEntity;
import com.pubg.entity.WalletEntity;

/**
 * @author Anigam
 *
 */
public interface AdminRepository {

	public void insertMatch(MatchesDTO matchesDTO);

	public void updateMatch(MatchesDTO matchesDTO);

	public void updateBalance(WalletDTO walletDTO);

	public WalletEntity getBalance(String userId);

	public void updateRoomIdPass(String roomId, String password, String matchId);

	public void expireAMatch(String matchId, String matchStatus);

	public void updatePrizes(TreeMap<String, String> updatePrizesReq);

	public List<PaymentEntity> getWithdrawRequests();
	
}
