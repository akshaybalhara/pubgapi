package com.pubg.repository.impl;

import java.util.Date;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pubg.dto.WalletDTO;
import com.pubg.entity.PaymentEntity;
import com.pubg.exception.PUBGBusinessException;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.repository.PaymentRepository;
import com.pubg.service.AdminService;

/**
 * UtilRepositoryImpl : Implementation class of UtilRepository.
 * 
 * @author Prolifics
 *
 */
@Repository
public class PaymentRepositoryImpl implements PaymentRepository, MessageConstants {
	
	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private AdminService adminService;

	@Override
	public void addPaymentTransaction(TreeMap<String, String> parameters,String userId,String matchId) {
		logger.info("Entering into PaymentRepositoryImpl.addPaymentTransaction()");
		PaymentEntity entity = new PaymentEntity();
		if(matchId == null || matchId.isEmpty()) {
			matchId = "00";
		}
		entity = updatePaymentEntity(parameters,entity);
		entity.setMatchId(Integer.parseInt(matchId));
		entity.setUserId(userId);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			//Start of transaction
			entityTransaction.begin();
			//persist method is used to do insertion of entities into their DB table.
			entityManager.persist(entity);
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
		
		logger.info("Exiting from PaymentRepositoryImpl.addPaymentTransaction()");
	}

	private PaymentEntity updatePaymentEntity(TreeMap<String, String> parameters, PaymentEntity entity) {
		logger.info("Entering into PaymentRepositoryImpl.updatePaymentEntity()");
		if(!parameters.isEmpty()) {
			entity.setAmount(Double.parseDouble(parameters.get("TXNAMOUNT")));
			entity.setBankName(parameters.get("BANKNAME"));
			entity.setBankTxnId(parameters.get("BANKTXNID"));
			entity.setOrderId(parameters.get("ORDERID"));
			entity.setPaymentDate(new Date());
			entity.setPaymentMode(parameters.get("PAYMENTMODE"));
			entity.setPaymentStatus(parameters.get("STATUS"));
			entity.setResponseCode(parameters.get("RESPCODE"));
			entity.setResponseMsg(parameters.get("RESPMSG"));
			entity.setTransactionId(parameters.get("TXNID"));
		}
		logger.info("Exiting from PaymentRepositoryImpl.updatePaymentEntity()");
		return entity;
	}

	@Override
	public PaymentEntity addWalletTransaction(String userId, int matchId, int amount) {
		logger.info("Entering into PaymentRepositoryImpl.addWalletTransaction()");
		PaymentEntity entity = preparePaymentEntityForWallet(userId,matchId,amount);
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			//Start of transaction
			entityTransaction.begin();
			//persist method is used to do insertion of entities into their DB table.
			entityManager.persist(entity);
			//commit will actually make this transaction persist in DB.
			entityTransaction.commit();
			
			//Deduct WalletBalance
			WalletDTO walletDTO = new WalletDTO();
			walletDTO.setOperation("Deduction");
			walletDTO.setAmount(amount);
			walletDTO.setUserId(userId);
			adminService.updateWallet(walletDTO);
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
		logger.info("Exiting from PaymentRepositoryImpl.addWalletTransaction()");
		return entity;
	}
	
	private PaymentEntity preparePaymentEntityForWallet(String userId,int matchId,int amount) {
		logger.info("Entering into PaymentRepositoryImpl.preparePaymentEntityForWallet()");
		PaymentEntity entity = new PaymentEntity();
		entity.setAmount(amount);
		entity.setBankName("RewardPlot Wallet");
		entity.setBankTxnId("WALLET_"+new Date().getTime());
		entity.setMatchId(matchId);
		entity.setOrderId("RP_ORD_"+new Date().getTime());
		entity.setPaymentDate(new Date());
		entity.setPaymentMode("Wallet");
		entity.setPaymentStatus("SUCCESS");
		entity.setResponseCode("01");
		entity.setResponseMsg("Payment done using RewardzPlot Wallet.");
		entity.setTransactionId("RP_TXN_"+new Date().getTime());
		entity.setUserId(userId);
		logger.info("Exiting from PaymentRepositoryImpl.preparePaymentEntityForWallet()");
		return entity;
	}
	
	
		

}
