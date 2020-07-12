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

import com.pubg.entity.JoinedMatchesEntity;
import com.pubg.entity.MatchesEntity;
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

	@Override
	public MatchesEntity getMatchDetails(String matchId) {
		logger.info("Entering MatchesRepositoryImpl.getMatchDetails()");
		MatchesEntity match = new MatchesEntity();
		String selectQuery = "FROM MatchesEntity where matchId="+matchId;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		match = (MatchesEntity) entityManager.createQuery(selectQuery).getResultList().get(0);	
		entityManager.clear();
		entityManager.close();
		logger.info("Exiting MatchesRepositoryImpl.getMatchDetails()");
		return match;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getParticipantsList(String matchId) {
		logger.info("Entering MatchesRepositoryImpl.getParticipantsList()");
		List<JoinedMatchesEntity> joinedMatchesEntityList = new ArrayList<JoinedMatchesEntity>();
		String selectQuery = "FROM JoinedMatchesEntity where matchId="+matchId;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		joinedMatchesEntityList = (List<JoinedMatchesEntity>) entityManager.createQuery(selectQuery).getResultList();	
		List<String> participants = new ArrayList<String>();
		for (JoinedMatchesEntity joinedMatchesEntity : joinedMatchesEntityList) {
			participants.add(joinedMatchesEntity.getUserId());
		}
		entityManager.clear();
		entityManager.close();
		logger.info("Exiting MatchesRepositoryImpl.getParticipantsList()");
		return participants;
	}

	@Override
	public void joinMatch(JoinedMatchesEntity request) {
		boolean entryFeesPaid = checkEntryFeesStatus(request.getUserId(),request.getMatchId());
		//proceed further to join the match only if user paid entry fees.
		if(entryFeesPaid) {
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction entityTransaction = entityManager.getTransaction();
			try {
				//Start of transaction
				entityTransaction.begin();
				//persist method is used to do insertion of entities into their DB table.
				entityManager.persist(request);
				//commit will actually make this transaction persist in DB.
				entityTransaction.commit();
			} catch (RuntimeException e) {
			    if (entityTransaction.isActive()) {
			        entityTransaction.rollback();
			    }
			    e.printStackTrace();
			    throw new PUBGBusinessException(SOMETHING_WENT_WRONG,SOMETHING_WENT_WRONG_MSG);
			} finally {
				entityManager.clear();
			    entityManager.close();
			}
		}else {
			throw new PUBGBusinessException("FEE_002","Entry fees not paid by the user.");
		}
		
	}

	private boolean checkEntryFeesStatus(String userId, int matchId) {
		// TODO Auto-generated method stub
		return true;
	}

		

}
