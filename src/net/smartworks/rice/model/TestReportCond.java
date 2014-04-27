/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.rice.model;

import java.util.Date;

import net.smartworks.common.Cond;

public class TestReportCond extends Cond {

	private String id;
	private String project;
	private String lotNo;
	private String lotNoLike;
	private Date dateTime;
	private Date dateTimeFrom;
	private Date dateTimeTo;
	private int fairQualityCount;
	private int faultCount;
	private String firstSensorId;

	public int getTotalTestCount() {
		return fairQualityCount + faultCount;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getLotNoLike() {
		return lotNoLike;
	}
	public void setLotNoLike(String lotNoLike) {
		if (lotNoLike != null && lotNoLike.indexOf("%") == -1) {
			this.lotNoLike = "%"+lotNoLike+"%";
		} else {
			this.lotNoLike = lotNoLike;
		}
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public Date getDateTimeFrom() {
		return dateTimeFrom;
	}
	public void setDateTimeFrom(Date dateTimeFrom) {
		this.dateTimeFrom = dateTimeFrom;
	}
	public Date getDateTimeTo() {
		return dateTimeTo;
	}

	public void setDateTimeTo(Date dateTimeTo) {
		this.dateTimeTo = dateTimeTo;
	}

	public int getFairQualityCount() {
		return fairQualityCount;
	}
	public void setFairQualityCount(int fairQualityCount) {
		this.fairQualityCount = fairQualityCount;
	}
	public int getFaultCount() {
		return faultCount;
	}
	public void setFaultCount(int faultCount) {
		this.faultCount = faultCount;
	}
	public String getFirstSensorId() {
		return firstSensorId;
	}
	public void setFirstSensorId(String firstSensorId) {
		this.firstSensorId = firstSensorId;
	}
}
