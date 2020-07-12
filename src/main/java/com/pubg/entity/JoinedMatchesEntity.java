package com.pubg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.pubg.compositeids.JoinedMatchesId;

@Entity
@Table(name = "joined_matches_tbl")
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(JoinedMatchesId.class)
public class JoinedMatchesEntity {
	
	@Id
	@Column(name="userId")
    private String userId;
	
	@Id
	@Column(name="matchId")
    private int matchId;
	
	@Column(name="rank")
    private int rank;
	
	@Column(name="kills")
    private int kills;
	
	@Column(name="feeStatus")
    private String feeStatus;
	
	@Column(name="win_amount")
    private int winningAmount;
	
	@Column(name="payment")
    private String paymentToUser;
	
	@Column(name="submissionDate")
    private Date submissionDate;

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
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return the kills
	 */
	public int getKills() {
		return kills;
	}

	/**
	 * @param kills the kills to set
	 */
	public void setKills(int kills) {
		this.kills = kills;
	}

	/**
	 * @return the feeStatus
	 */
	public String getFeeStatus() {
		return feeStatus;
	}

	/**
	 * @param feeStatus the feeStatus to set
	 */
	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}

	/**
	 * @return the winningAmount
	 */
	public int getWinningAmount() {
		return winningAmount;
	}

	/**
	 * @param winningAmount the winningAmount to set
	 */
	public void setWinningAmount(int winningAmount) {
		this.winningAmount = winningAmount;
	}

	/**
	 * @return the paymentToUser
	 */
	public String getPaymentToUser() {
		return paymentToUser;
	}

	/**
	 * @param paymentToUser the paymentToUser to set
	 */
	public void setPaymentToUser(String paymentToUser) {
		this.paymentToUser = paymentToUser;
	}

	/**
	 * @return the submissionDate
	 */
	public Date getSubmissionDate() {
		return submissionDate;
	}

	/**
	 * @param submissionDate the submissionDate to set
	 */
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	@Override
	public String toString() {
		return "JoinedMatchesEntity [userId=" + userId + ", matchId=" + matchId + ", rank=" + rank + ", kills=" + kills
				+ ", feeStatus=" + feeStatus + ", winningAmount=" + winningAmount + ", paymentToUser=" + paymentToUser
				+ ", submissionDate=" + submissionDate + "]";
	}

}
