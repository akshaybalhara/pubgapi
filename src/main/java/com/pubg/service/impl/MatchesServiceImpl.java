package com.pubg.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubg.dto.StatusDTO;
import com.pubg.entity.JoinedMatchesEntity;
import com.pubg.entity.MatchesEntity;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.repository.MatchesRepository;
import com.pubg.service.BaseService;
import com.pubg.service.MatchesService;


/**
 * UserServiceImpl holds all the Service methods that are 
 * utilized to get User Information and User Authentication Flow.
 *  
 * @author Prolifics
 *
 */
@Service
public class MatchesServiceImpl  extends BaseService implements MatchesService, MessageConstants{

	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MatchesRepository matchesRepository;

	@Override
	public List<MatchesEntity> listAllMatches() {
		logger.info("Entering into MatchesServiceImpl.listAllMatches()");
		List<MatchesEntity> matches = matchesRepository.getAllMatches();
		logger.info("Exiting MatchesServiceImpl.listAllMatches()");
		return matches;
	}

	@Override
	public MatchesEntity getMatchById(String matchId) {
		logger.info("Entering into MatchesServiceImpl.getMatchById()");
		MatchesEntity match = matchesRepository.getMatchDetails(matchId);
		logger.info("Exiting MatchesServiceImpl.getMatchById()");
		return match;
	}

	@Override
	public List<String> getParticipants(String matchId) {
		logger.info("Entering into MatchesServiceImpl.getParticipants()");
		List<String> participants = matchesRepository.getParticipantsList(matchId);
		logger.info("Exiting MatchesServiceImpl.getParticipants()");
		return participants;
	}

	@Override
	public StatusDTO joinAMatch(JoinedMatchesEntity request) {
		logger.info("Entering into MatchesServiceImpl.joinAMatch()");
		matchesRepository.joinMatch(request);
		StatusDTO statusDTO = new StatusDTO(true, "JON_001", "Successfully joined the match.");
		logger.info("Exiting MatchesServiceImpl.joinAMatch()");
		return statusDTO;
	}

	@Override
	public StatusDTO checkAlreadyJoined(String userId, String matchId) {
		logger.info("Entering into MatchesServiceImpl.checkAlreadyJoined()");
		boolean flag = matchesRepository.isAlreadyJoinedMatch(userId,matchId);
		StatusDTO statusDTO = new StatusDTO(flag, "AJN_001", "Successfully checked");
		logger.info("Exiting MatchesServiceImpl.checkAlreadyJoined()");
		return statusDTO;
	}

	@Override
	public List<MatchesEntity> listMyMatches(String userId) {
		logger.info("Entering into MatchesServiceImpl.listMyMatches()");
		List<MatchesEntity> matches = matchesRepository.getMatchesByUserId(userId);
		logger.info("Exiting MatchesServiceImpl.listMyMatches()");
		return matches;
	}

}
