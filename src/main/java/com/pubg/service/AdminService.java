/**
 * 
 */
package com.pubg.service;

import com.pubg.dto.MatchesDTO;
import com.pubg.dto.StatusDTO;

/**
 * @author Anigam
 *
 */
public interface AdminService {
		
	public StatusDTO processMatches(MatchesDTO matchesDTO);
	
}
