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
@Table(name = "users_tbl")
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationEntity {
	
	@Id
	@Column(name="userId")
    private String userId;
	
	@Column(name="secretKey")
    private String password;
	
	@Column(name="phone")
    private String phone;
	
	@Column(name="email")
    private String email;
	
	@Column(name="fname")
    private String fname;
	
	@Column(name="lname")
    private String lname;
	
	@Column(name="gender")
    private String gender;
	
	@Column(name="role")
    private String role;
	
	@Column(name="otp")
    private String otp;
	
	@Column(name="verificationLink")
    private String verificationLink;
	
	@Column(name="status")
    private String status;
	
	@Column(name="submissionDate")
    private Date submissionDate;
	
	@Column(name="resetPin")
    private String resetPin;
	
	@Column(name="pubgUsername")
    private String pubgUsername;

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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
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
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
	 * @return the verificationLink
	 */
	public String getVerificationLink() {
		return verificationLink;
	}

	/**
	 * @param verificationLink the verificationLink to set
	 */
	public void setVerificationLink(String verificationLink) {
		this.verificationLink = verificationLink;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the resetPin
	 */
	public String getResetPin() {
		return resetPin;
	}

	/**
	 * @param resetPin the resetPin to set
	 */
	public void setResetPin(String resetPin) {
		this.resetPin = resetPin;
	}

	/**
	 * @return the pubgUsername
	 */
	public String getPubgUsername() {
		return pubgUsername;
	}

	/**
	 * @param pubgUsername the pubgUsername to set
	 */
	public void setPubgUsername(String pubgUsername) {
		this.pubgUsername = pubgUsername;
	}

	@Override
	public String toString() {
		return "RegistrationEntity [userId=" + userId + ", password=" + password + ", phone=" + phone + ", email="
				+ email + ", fname=" + fname + ", lname=" + lname + ", gender=" + gender + ", role=" + role + ", otp="
				+ otp + ", verificationLink=" + verificationLink + ", status=" + status + ", submissionDate="
				+ submissionDate + ", resetPin=" + resetPin + ", pubgUsername=" + pubgUsername + "]";
	}

}
