package com.pubg.service;

import java.util.List;

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
	
	public List<MatchesEntity> listAllMatches(String leagueType);
}
