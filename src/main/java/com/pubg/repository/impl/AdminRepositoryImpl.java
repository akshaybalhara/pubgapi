/**
 * 
 */
package com.pubg.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pubg.dto.MatchesDTO;
import com.pubg.dto.WalletDTO;
import com.pubg.entity.MatchesEntity;
import com.pubg.entity.PaymentEntity;
import com.pubg.entity.WalletEntity;
import com.pubg.exception.PUBGBusinessException;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.repository.AdminRepository;
import com.pubg.service.MatchesService;

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
	
	@Autowired
	private MatchesService matchesService;

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
	
	@Override
	public void updateMatch(MatchesDTO matchesDTO) {
		logger.info("Entering AdminRepositoryImpl.updateMatch()");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		MatchesEntity matchesEntity = matchesService.getMatchById(Integer.toString(matchesDTO.getMatchId()));
		matchesEntity.setPlayersJoined(matchesEntity.getPlayersJoined()+1);
		
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			//Start of transaction
			entityTransaction.begin();
			//persist method is used to do insertion of entities into their DB table.
			entityManager.merge(matchesEntity);
			
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
		logger.info("Exiting AdminRepositoryImpl.updateMatch()");
	}
	
	private MatchesEntity updateMatchesEntity(MatchesDTO matchesDTO , MatchesEntity entity){
		logger.info("Entering AdminRepositoryImpl.updateMatchesEntity()");
		switch(matchesDTO.getOperation()){
		case "Insert":
			entity.setEntryFee(matchesDTO.getEntryFee());
			entity.setFirstPrize(matchesDTO.getFirstPrize());
			entity.setSecondPrize(matchesDTO.getSecondPrize());
			entity.setThirdPrize(matchesDTO.getThirdPrize());
			entity.setFourToFifthPrize(matchesDTO.getFourToFifthPrize()> 0 ? matchesDTO.getFourToFifthPrize() : 0);
			entity.setSixthToTenthPrize(matchesDTO.getSixthToTenthPrize()> 0 ? matchesDTO.getSixthToTenthPrize() : 0);
			entity.setElevenToFifteenPrize(matchesDTO.getElevenToFifteenPrize()> 0 ? matchesDTO.getElevenToFifteenPrize() : 0);
			entity.setSixteenToTwentyPrize(matchesDTO.getSixteenToTwentyPrize()> 0 ? matchesDTO.getSixteenToTwentyPrize() : 0);
			entity.setPerKill(matchesDTO.getPerKill());
			entity.setMatchType(matchesDTO.getMatchType());
			entity.setMap(matchesDTO.getMap());
			entity.setStatus("Coming Soon");
			entity.setDateAndTime(matchesDTO.getDateAndTime());
			entity.setSubmissionDate(new Date());
			entity.setRoomSize(matchesDTO.getRoomSize() > 0 ? matchesDTO.getRoomSize() : 100);
			break;
		}
		logger.info("Exiting AdminRepositoryImpl.updateMatchesEntity()");
		return entity;
	}

	//TODO add code for transaction entry for updating winning amount
	@Override
	public void updateBalance(WalletDTO walletDTO) {
		logger.info("Entering AdminRepositoryImpl.updateBalance()");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			//Start of transaction
			entityTransaction.begin();
			WalletEntity entity = getBalance(walletDTO.getUserId());
			if(entity == null) {
				entity = new WalletEntity();
				entity.setUserId(walletDTO.getUserId());
			}
			if(walletDTO.getOperation().equals("Deduction")) {
				entity.setBalance(entity.getBalance() - walletDTO.getAmount());
			}else if(walletDTO.getOperation().equals("Addition")) {
				entity.setBalance(entity.getBalance() + walletDTO.getAmount());
			}
			entity.setLastUpdated(new Date());
			if(walletDTO.isUpdatingWinningAmount()) {
				PaymentEntity paymentEntity = new PaymentEntity();
				paymentEntity.setAmount(walletDTO.getAmount());
				paymentEntity.setBankName("RewardzPlotWallet");
				paymentEntity.setBankTxnId("WIN_AMT_"+new Date().getTime());
				int matchId = walletDTO.getMatchId() > 0 ? walletDTO.getMatchId() : 0;
				paymentEntity.setMatchId(matchId);
				paymentEntity.setOrderId("RP_WIN_"+matchId+"_"+new Date().getTime());
				paymentEntity.setPaymentDate(new Date());
				paymentEntity.setPaymentMode("RP_Wallet");
				paymentEntity.setPaymentStatus("SUCCESS");
				paymentEntity.setResponseCode("01");
				paymentEntity.setResponseMsg("Payment amount added against match id: "+matchId);
				paymentEntity.setTransactionId("RP_TXN_"+new Date().getTime());
				paymentEntity.setUserId(walletDTO.getUserId());
				entityManager.persist(paymentEntity);
			}
			
			//persist method is used to do insertion of entities into their DB table.
			entityManager.merge(entity);
			
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
		logger.info("Exiting AdminRepositoryImpl.updateBalance()");
	}

	@Override
	public WalletEntity getBalance(String userId) {
		logger.info("Entering AdminRepositoryImpl.getBalance()");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		WalletEntity entity = entityManager.find(WalletEntity.class, userId);
		if(entity == null) {
			entity = new WalletEntity();
			entity.setBalance(0);
			entity.setLastUpdated(new Date());
			entity.setUserId(userId);
		}
		entityManager.clear();
		entityManager.close();
		logger.info("Exiting AdminRepositoryImpl.getBalance()");
		return entity;
	}

	@Override
	public void updateRoomIdPass(String roomId, String password, String matchId) {
		logger.info("Entering AdminRepositoryImpl.updateRoomIdPass()");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		MatchesEntity matchesEntity = matchesService.getMatchById(matchId);
		matchesEntity.setRoomId(roomId);
		matchesEntity.setPassword(password);
		
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			//Start of transaction
			entityTransaction.begin();
			//persist method is used to do insertion of entities into their DB table.
			entityManager.merge(matchesEntity);
			
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
		logger.info("Exiting AdminRepositoryImpl.updateRoomIdPass()");
	}

	@Override
	public void expireAMatch(String matchId, String matchStatus) {
		logger.info("Entering AdminRepositoryImpl.expireAMatch()");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		MatchesEntity matchesEntity = matchesService.getMatchById(matchId);
		matchesEntity.setStatus(matchStatus);
		
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			//Start of transaction
			entityTransaction.begin();
			//persist method is used to do insertion of entities into their DB table.
			entityManager.merge(matchesEntity);
			
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
		logger.info("Exiting AdminRepositoryImpl.expireAMatch()");
		
	}

	@Override
	public void updatePrizes(TreeMap<String, String> updatePrizesReq) {
		logger.info("Entering AdminRepositoryImpl.updatePrizes()");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		MatchesEntity matchesEntity = matchesService.getMatchById(updatePrizesReq.get("matchId"));
		matchesEntity.setFirstPrize(Integer.parseInt(updatePrizesReq.get("firstPrize")));
		matchesEntity.setSecondPrize(Integer.parseInt(updatePrizesReq.get("secondPrize")));
		matchesEntity.setThirdPrize(Integer.parseInt(updatePrizesReq.get("thirdPrize")));
		matchesEntity.setFourToFifthPrize(Integer.parseInt(updatePrizesReq.get("fourToFifthPrize")));
		matchesEntity.setSixthToTenthPrize(Integer.parseInt(updatePrizesReq.get("sixthToTenthPrize")));
		matchesEntity.setElevenToFifteenPrize(Integer.parseInt(updatePrizesReq.get("elevenToFifteenPrize")));
		matchesEntity.setSixteenToTwentyPrize(Integer.parseInt(updatePrizesReq.get("sixteenToTwentyPrize")));
		
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			//Start of transaction
			entityTransaction.begin();
			//persist method is used to do insertion of entities into their DB table.
			entityManager.merge(matchesEntity);
			
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
		logger.info("Exiting AdminRepositoryImpl.updatePrizes()");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentEntity> getWithdrawRequests() {
		logger.info("Entering AdminRepositoryImpl.getWithdrawRequests()");
		List<PaymentEntity> withdrawlRequests = new ArrayList<PaymentEntity>();
		String selectQuery = "FROM PaymentEntity as pe where pe.bankTxnId like :likeParam and pe.paymentStatus='PENDING'";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		withdrawlRequests = entityManager.createQuery(selectQuery).setParameter("likeParam", "%WITHDRAW_%").getResultList();
		entityManager.clear();
		entityManager.close();
		logger.info("Exiting AdminRepositoryImpl.getWithdrawRequests()");
		return withdrawlRequests;
	}
	
}
