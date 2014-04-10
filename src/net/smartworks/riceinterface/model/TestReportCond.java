/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.riceinterface.model;

import java.util.Date;

import net.smartworks.common.Cond;

public class TestReportCond extends Cond {

	private String id;
	private String project;
	private String lotNo;
	private Date dateTime;
	private int fairQualityCount;
	private int faultCount;
	private String sensorSerialNo;

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
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
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
	public String getSensorSerialNo() {
		return sensorSerialNo;
	}
	public void setSensorSerialNo(String sensorSerialNo) {
		this.sensorSerialNo = sensorSerialNo;
	}
}
