package com.pubg.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pubg.entity.MatchesEntity;

@Repository
public interface MatchesRepository {
	
	public List<MatchesEntity> getAllMatches(String leagueType);

}
