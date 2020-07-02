/**
 * 
 */
package com.pubg.dto;

import java.util.Date;

import javax.persistence.Column;

/**
 * @author Anigam
 *
 */
public class MatchesDTO {

    private int entryFee;
	
    private int firstPrize;
	
    private int secondPrize;
	
    private int thirdPrize;
	
    private int perKill;
	
    private String matchType;
	
    private String map;
	
    private String leagueType;
	
    private String status;
	
    private Date dateAndTime;
	
    private Date createdDate;
    
    private String operation;

	/**
	 * @return the entryFee
	 */
	public int getEntryFee() {
		return entryFee;
	}

	/**
	 * @param entryFee the entryFee to set
	 */
	public void setEntryFee(int entryFee) {
		this.entryFee = entryFee;
	}

	/**
	 * @return the firstPrize
	 */
	public int getFirstPrize() {
		return firstPrize;
	}

	/**
	 * @param firstPrize the firstPrize to set
	 */
	public void setFirstPrize(int firstPrize) {
		this.firstPrize = firstPrize;
	}

	/**
	 * @return the secondPrize
	 */
	public int getSecondPrize() {
		return secondPrize;
	}

	/**
	 * @param secondPrize the secondPrize to set
	 */
	public void setSecondPrize(int secondPrize) {
		this.secondPrize = secondPrize;
	}

	/**
	 * @return the thirdPrize
	 */
	public int getThirdPrize() {
		return thirdPrize;
	}

	/**
	 * @param thirdPrize the thirdPrize to set
	 */
	public void setThirdPrize(int thirdPrize) {
		this.thirdPrize = thirdPrize;
	}

	/**
	 * @return the perKill
	 */
	public int getPerKill() {
		return perKill;
	}

	/**
	 * @param perKill the perKill to set
	 */
	public void setPerKill(int perKill) {
		this.perKill = perKill;
	}

	/**
	 * @return the matchType
	 */
	public String getMatchType() {
		return matchType;
	}

	/**
	 * @param matchType the matchType to set
	 */
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	/**
	 * @return the map
	 */
	public String getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(String map) {
		this.map = map;
	}

	/**
	 * @return the leagueType
	 */
	public String getLeagueType() {
		return leagueType;
	}

	/**
	 * @param leagueType the leagueType to set
	 */
	public void setLeagueType(String leagueType) {
		this.leagueType = leagueType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the dateAndTime
	 */
	public Date getDateAndTime() {
		return dateAndTime;
	}

	/**
	 * @param dateAndTime the dateAndTime to set
	 */
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MatchesDTO [entryFee=" + entryFee + ", firstPrize=" + firstPrize + ", secondPrize=" + secondPrize
				+ ", thirdPrize=" + thirdPrize + ", perKill=" + perKill + ", matchType=" + matchType + ", map=" + map
				+ ", leagueType=" + leagueType + ", status=" + status + ", dateAndTime=" + dateAndTime
				+ ", createdDate=" + createdDate + ", operation=" + operation + "]";
	}
    
}
