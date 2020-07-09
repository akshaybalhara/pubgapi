package com.pubg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "app_update")
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUpdateEntity {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="app_url")
	private String appUrl;
	
	@Column(name="app_version")
	private double appVersion;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the appUrl
	 */
	public String getAppUrl() {
		return appUrl;
	}

	/**
	 * @param appUrl the appUrl to set
	 */
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	/**
	 * @return the appVersion
	 */
	public double getAppVersion() {
		return appVersion;
	}

	/**
	 * @param appVersion the appVersion to set
	 */
	public void setAppVersion(double appVersion) {
		this.appVersion = appVersion;
	}

	@Override
	public String toString() {
		return "AppUpdateEntity [id=" + id + ", appUrl=" + appUrl + ", appVersion=" + appVersion + "]";
	}

}
