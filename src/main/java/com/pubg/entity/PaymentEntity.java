package com.pubg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "payment_tbl")
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentEntity {
	
	@Id
	@Column(name="order_id")
    private String orderId;
	
	@Column(name="user_id")
    private String userId;
	
	@Column(name="match_id")
    private int matchId;
	
	@Column(name="amount")
    private double amount;
	
	@Column(name="bank_name")
    private String bankName;
	
	@Column(name="bank_txn_id")
    private String bankTxnId;
	
	@Column(name="resp_code")
    private String responseCode;
	
	@Column(name="resp_msg")
    private String responseMsg;
	
	@Column(name="txn_id")
    private String transactionId;
	
	@Column(name="payment_mode")
    private String paymentMode;
	
	@Column(name="payment_status")
    private String paymentStatus;
	
	@Column(name="payment_date")
    private Date paymentDate;

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the matchId
	 */
	public int getMatchId() {
		return matchId;
	}

	/**
	 * @param matchId the matchId to set
	 */
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankTxnId
	 */
	public String getBankTxnId() {
		return bankTxnId;
	}

	/**
	 * @param bankTxnId the bankTxnId to set
	 */
	public void setBankTxnId(String bankTxnId) {
		this.bankTxnId = bankTxnId;
	}

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the responseMsg
	 */
	public String getResponseMsg() {
		return responseMsg;
	}

	/**
	 * @param responseMsg the responseMsg to set
	 */
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "PaymentEntity [orderId=" + orderId + ", userId=" + userId + ", matchId=" + matchId + ", amount="
				+ amount + ", bankName=" + bankName + ", bankTxnId=" + bankTxnId + ", responseCode=" + responseCode
				+ ", responseMsg=" + responseMsg + ", transactionId=" + transactionId + ", paymentMode=" + paymentMode
				+ ", paymentStatus=" + paymentStatus + ", paymentDate=" + paymentDate
				+ "]";
	}

}
