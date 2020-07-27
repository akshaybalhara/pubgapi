/**
 * 
 */
package com.pubg.repository;

import com.pubg.dto.MatchesDTO;
import com.pubg.dto.WalletDTO;
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
	
}
