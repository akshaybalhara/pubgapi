/**
 * 
 */
package com.pubg.service;

import com.pubg.dto.MatchesDTO;
import com.pubg.dto.StatusDTO;
import com.pubg.dto.WalletDTO;
import com.pubg.entity.WalletEntity;

/**
 * @author Anigam
 *
 */
public interface AdminService {
		
	public StatusDTO processMatches(MatchesDTO matchesDTO);

	public StatusDTO updateWallet(WalletDTO walletDTO);

	public WalletEntity getWalletBalance(String userId);
	
}
