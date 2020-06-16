package com.pubg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "USER_NOTIFICATION")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)

@NamedNativeQueries({    
    @NamedNativeQuery(
            name    =   "getActiveNotificationsOfUser",
            query   =   "SELECT NOTIFICATION_ID, EMPLOYEE_ID, APPLICATION_TYPE, NOTIFICATION, NOTIFICATION_FLAG, NOTIFICATION_DATE, META_INFO, DELETED FROM USER_NOTIFICATION"
            		   + " WHERE EMPLOYEE_ID = ? AND NOTIFICATION_FLAG = 0 AND DELETED = 0 ORDER BY NOTIFICATION_ID DESC", resultClass=NotificationEntity.class
    ),
    @NamedNativeQuery(
            name    =   "getAllNotificationsOfUser",
            query   =   "SELECT NOTIFICATION_ID, EMPLOYEE_ID, APPLICATION_TYPE, NOTIFICATION, NOTIFICATION_FLAG, NOTIFICATION_DATE, META_INFO, DELETED FROM USER_NOTIFICATION"
            		   + " WHERE EMPLOYEE_ID = ? AND DELETED = 0 ORDER BY NOTIFICATION_ID DESC", resultClass=NotificationEntity.class
    )
})

public class NotificationEntity {
	
	@Id
	@Column(name="NOTIFICATION_ID")
    private int notificationId;
	
	@Column(name="EMPLOYEE_ID")
    private String employeeId;
	
	@Column(name="APPLICATION_TYPE")
    private String appType;
	
	@Column(name="NOTIFICATION")
    private String notification;
	
	@Column(name="NOTIFICATION_FLAG")
    private boolean actionTaken;
	
	@Column(name="NOTIFICATION_DATE")
    private Date notificationDate;
	
	@Column(name="META_INFO")
    private String metaInfo;
	
	@Column(name="DELETED")
    private boolean deleted;

	/**
	 * @return the notificationId
	 */
	public int getNotificationId() {
		return notificationId;
	}

	/**
	 * @param notificationId the notificationId to set
	 */
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the notification
	 */
	public String getNotification() {
		return notification;
	}

	/**
	 * @param notification the notification to set
	 */
	public void setNotification(String notification) {
		this.notification = notification;
	}		

	/**
	 * @return the actionTaken
	 */
	public boolean isActionTaken() {
		return actionTaken;
	}

	/**
	 * @param actionTaken the actionTaken to set
	 */
	public void setActionTaken(boolean actionTaken) {
		this.actionTaken = actionTaken;
	}

	/**
	 * @return the notificationDate
	 */
	public Date getNotificationDate() {
		return notificationDate;
	}

	/**
	 * @param notificationDate the notificationDate to set
	 */
	public void setNotificationDate(Date notificationDate) {
		this.notificationDate = notificationDate;
	}

	/**
	 * @return the metaInfo
	 */
	public String getMetaInfo() {
		return metaInfo;
	}

	/**
	 * @param metaInfo the metaInfo to set
	 */
	public void setMetaInfo(String metaInfo) {
		this.metaInfo = metaInfo;
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the appType
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * @param appType the appType to set
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	@Override
	public String toString() {
		return "NotificationEntity [notificationId=" + notificationId + ", employeeId=" + employeeId + ", appType="
				+ appType + ", notification=" + notification + ", actionTaken=" + actionTaken + ", notificationDate="
				+ notificationDate + ", metaInfo=" + metaInfo + ", deleted=" + deleted + "]";
	}
	
}
