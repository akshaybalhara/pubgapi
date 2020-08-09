package com.pubg.service.impl;

import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubg.entity.PaymentEntity;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.repository.PaymentRepository;
import com.pubg.service.BaseService;
import com.pubg.service.PaymentService;


/**
 * UserServiceImpl holds all the Service methods that are 
 * utilized to get User Information and User Authentication Flow.
 *  
 * @author Prolifics
 *
 */
@Service
public class PaymentServiceImpl  extends BaseService implements PaymentService, MessageConstants{

	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public void addPaymentTransactionDetails(TreeMap<String, String> parameters, String userId, String matchId) {
		logger.info("Entering into PaymentServiceImpl.addPaymentTransactionDetails()");
		paymentRepository.addPaymentTransaction(parameters,userId,matchId);
		logger.info("Exiting from PaymentServiceImpl.addPaymentTransactionDetails()");
	}

	@Override
	public PaymentEntity addWalletTransactionDetails(String userId, int matchId, int amount) {
		logger.info("Entering into PaymentServiceImpl.addPaymentTransactionDetails()");
		PaymentEntity entity = paymentRepository.addWalletTransaction(userId,matchId,amount);
		logger.info("Exiting from PaymentServiceImpl.addPaymentTransactionDetails()");
		return entity;
	}

	@Override
	public PaymentEntity addWithdrawRequest(String userId, int matchId, int amount) {
		logger.info("Entering into PaymentServiceImpl.addPaymentTransactionDetails()");
		PaymentEntity entity = paymentRepository.addWithdrawTransaction(userId,matchId,amount);
		logger.info("Exiting from PaymentServiceImpl.addPaymentTransactionDetails()");
		return entity;
	}

	@Override
	public List<PaymentEntity> getTransactionsByUserId(String userId) {
		logger.info("Entering into PaymentServiceImpl.getTransactionsByUserId()");
		List<PaymentEntity> transactions = paymentRepository.getTransactionsByUserId(userId);
		logger.info("Exiting from PaymentServiceImpl.getTransactionsByUserId()");
		return transactions;
	}

}
