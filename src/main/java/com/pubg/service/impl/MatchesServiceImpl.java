package com.pubg.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
