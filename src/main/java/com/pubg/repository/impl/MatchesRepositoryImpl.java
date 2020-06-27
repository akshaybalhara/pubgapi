package com.pubg.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.pubg.entity.MatchesEntity;
import com.pubg.entity.RegistrationEntity;
import com.pubg.exception.PUBGBusinessException;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.repository.MatchesRepository;

/**
 * UtilRepositoryImpl : Implementation class of UtilRepository.
 * 
 * @author Prolifics
 *
 */
@Repository
public class MatchesRepositoryImpl implements MatchesRepository, MessageConstants {
	
	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	/**
	 * EntityManager instance is injected by Spring Framework.
	 * 
	 * This is used to manage all the Persistent entities
	 * defined in the System.
	 */

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<MatchesEntity> getAllMatches(String leagueType) {
		logger.info("Entering MatchesRepositoryImpl.getAllMatches()");
		List<MatchesEntity> matches = new ArrayList<MatchesEntity>();
		String selectQuery = "FROM MatchesEntity where status='Coming Soon' and leagueType='"+leagueType+"'";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		matches = entityManager.createQuery(selectQuery).getResultList();	
		entityManager.clear();
		entityManager.close();
		logger.info("Exiting MatchesRepositoryImpl.getAllMatches()");
		return matches;
	}

		

}
