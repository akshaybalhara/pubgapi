package com.pubg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pubg.dto.StatusDTO;

@Entity
@Table(name = "users_tbl")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEntity {
	
	@Id
	@Column(name="userId")
    private String userId;
	
	@Column(name="secretKey")
    private String password;
	
	@Column(name="phone")
    private String phone;
	
	@Column(name="country")
    private String country;
	
	@Column(name="state")
    private String state;
	
	@Column(name="pin")
    private String pin;
	
	@Column(name="submissionDate")
    private Date submissionDate;
	
	@Transient
	@JsonProperty("status")
	private StatusDTO statusDto;
	
	@Transient
	private String token;

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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @param pin the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
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
	 * @return the statusDto
	 */
	public StatusDTO getStatusDto() {
		return statusDto;
	}

	/**
	 * @param statusDto the statusDto to set
	 */
	public void setStatusDto(StatusDTO statusDto) {
		this.statusDto = statusDto;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "UserEntity [userId=" + userId + ", password=" + password + ", phone=" + phone + ", country=" + country
				+ ", state=" + state + ", pin=" + pin + ", submissionDate=" + submissionDate + ", statusDto="
				+ statusDto + ", token=" + token + "]";
	}

}
