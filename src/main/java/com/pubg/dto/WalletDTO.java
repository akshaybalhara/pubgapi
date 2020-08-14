package com.pubg.dto;

public class WalletDTO {
	
	private String userId;
	
	private int amount;
	
	private String operation;
	
	private boolean updatingWinningAmount;
	
	private int matchId;

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
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the updatingWinningAmount
	 */
	public boolean isUpdatingWinningAmount() {
		return updatingWinningAmount;
	}

	/**
	 * @param updatingWinningAmount the updatingWinningAmount to set
	 */
	public void setUpdatingWinningAmount(boolean updatingWinningAmount) {
		this.updatingWinningAmount = updatingWinningAmount;
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

	@Override
	public String toString() {
		return "WalletDTO [userId=" + userId + ", amount=" + amount + ", operation=" + operation
				+ ", updatingWinningAmount=" + updatingWinningAmount + ", matchId=" + matchId + "]";
	}

}
