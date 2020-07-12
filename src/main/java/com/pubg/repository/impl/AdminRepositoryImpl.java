/**
 * 
 */
package com.pubg.repository.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.pubg.dto.MatchesDTO;
import com.pubg.entity.MatchesEntity;
import com.pubg.exception.PUBGBusinessException;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.repository.AdminRepository;

/**
 * @author Anigam
 *
 */
@Repository
public class AdminRepositoryImpl implements AdminRepository,MessageConstants{
	
	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public void insertMatch(MatchesDTO matchesDTO) {
		// TODO Auto-generated method stub
		logger.info("Entering AdminRepositoryImpl.insertMatch()");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		MatchesEntity matchesEntity = new MatchesEntity();
		matchesEntity= updateMatchesEntity(matchesDTO, matchesEntity);
		
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			//Start of transaction
			entityTransaction.begin();
			//persist method is used to do insertion of entities into their DB table.
			entityManager.persist(matchesEntity);
			
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
		logger.info("Exiting AdminRepositoryImpl.insertMatch()");
	}
	
	private MatchesEntity updateMatchesEntity(MatchesDTO matchesDTO , MatchesEntity entity){
		logger.info("Entering AdminRepositoryImpl.updateMatchesEntity()");
		switch(matchesDTO.getOperation()){
		case "Insert":
			entity.setEntryFee(matchesDTO.getEntryFee());
			entity.setFirstPrize(matchesDTO.getFirstPrize());
			entity.setSecondPrize(matchesDTO.getSecondPrize());
			entity.setThirdPrize(matchesDTO.getThirdPrize());
			entity.setPerKill(matchesDTO.getPerKill());
			entity.setMatchType(matchesDTO.getMatchType());
			entity.setMap(matchesDTO.getMap());
			entity.setLeagueType(matchesDTO.getLeagueType());
			entity.setStatus("Coming Soon");
			entity.setDateAndTime(matchesDTO.getDateAndTime());
			entity.setSubmissionDate(new Date());
			break;
		}
		logger.info("Exiting AdminRepositoryImpl.updateMatchesEntity()");
		return entity;
	}
	
	
}
