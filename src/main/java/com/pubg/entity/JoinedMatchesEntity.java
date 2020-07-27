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
	
	@Column(name="feeStatus")
    private String feeStatus;
	
	@Column(name="status")
    private String status;
	
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

	@Override
	public String toString() {
		return "JoinedMatchesEntity [userId=" + userId + ", matchId=" + matchId + ", feeStatus=" + feeStatus
				+ ", status=" + status + ", submissionDate=" + submissionDate + "]";
	}

}
