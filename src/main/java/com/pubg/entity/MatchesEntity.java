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
@Table(name = "matches_tbl")
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchesEntity {
	
	@Id
	@Column(name="matchId")
    private int matchId;
	
	@Column(name="entryFee")
    private int entryFee;
	
	@Column(name="firstPrize")
    private int firstPrize;
	
	@Column(name="secondPrize")
    private int secondPrize;
	
	@Column(name="thirdPrize")
    private int thirdPrize;
	
	@Column(name="perKill")
    private int perKill;
	
	@Column(name="matchType")
    private String matchType;
	
	@Column(name="map")
    private String map;
	
	@Column(name="leagueType")
    private String leagueType;
	
	@Column(name="status")
    private String status;
	
	@Column(name="dateAndTime")
    private Date dateAndTime;
	
	@Column(name="submissionDate")
    private Date submissionDate;

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

	@Override
	public String toString() {
		return "MatchesEntity [matchId=" + matchId + ", entryFee=" + entryFee + ", firstPrize=" + firstPrize
				+ ", secondPrize=" + secondPrize + ", thirdPrize=" + thirdPrize + ", perKill=" + perKill
				+ ", matchType=" + matchType + ", map=" + map + ", leagueType=" + leagueType + ", status=" + status
				+ ", dateAndTime=" + dateAndTime + ", submissionDate=" + submissionDate + "]";
	}

}
