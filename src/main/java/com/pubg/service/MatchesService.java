package com.pubg.service;

import java.util.List;

import com.pubg.dto.StatusDTO;
import com.pubg.entity.JoinedMatchesEntity;
import com.pubg.entity.MatchesEntity;

/**
 * UtilService holds all the Service methods that are 
 * utilized for common services and are not relevant to
 * core System entities.
 *  
 * @author Prolifics
 *
 */
public interface MatchesService {	
	
	public List<MatchesEntity> listAllMatches();

	public MatchesEntity getMatchById(String matchId);

	public List<String> getParticipants(String matchId);

	public StatusDTO joinAMatch(JoinedMatchesEntity request);

	public StatusDTO checkAlreadyJoined(String userId, String matchId);
}
