package com.pubg.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pubg.entity.JoinedMatchesEntity;
import com.pubg.entity.MatchesEntity;

@Repository
public interface MatchesRepository {
	
	public List<MatchesEntity> getAllMatches();

	public MatchesEntity getMatchDetails(String matchId);

	public List<String> getParticipantsList(String matchId);

	public void joinMatch(JoinedMatchesEntity request);

	public boolean isAlreadyJoinedMatch(String userId, String matchId);

	public List<MatchesEntity> getMatchesByUserId(String userId);

}
