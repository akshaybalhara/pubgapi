package com.pubg.service;

import java.util.List;
import java.util.TreeMap;

import com.pubg.entity.PaymentEntity;

/**
 * UtilService holds all the Service methods that are 
 * utilized for common services and are not relevant to
 * core System entities.
 *  
 * @author Prolifics
 *
 */
public interface PaymentService {	
	
	public void addPaymentTransactionDetails(TreeMap<String, String> parameters, String userId, String matchId);

	public PaymentEntity addWalletTransactionDetails(String userId, int matchId, int amount);

	public PaymentEntity addWithdrawRequest(String userId, int matchId, int amount);

	public List<PaymentEntity> getTransactionsByUserId(String userId);
}
