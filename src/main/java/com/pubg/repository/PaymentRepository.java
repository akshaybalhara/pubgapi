package com.pubg.repository;

import java.util.List;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import com.pubg.entity.PaymentEntity;

@Repository
public interface PaymentRepository {
	
	public void addPaymentTransaction(TreeMap<String, String> parameters, String userId, String matchId);

	public PaymentEntity addWalletTransaction(String userId, int matchId, int amount);

	public PaymentEntity addWithdrawTransaction(String userId, int matchId, int amount);

	public List<PaymentEntity> getTransactionsByUserId(String userId);

}
