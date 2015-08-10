/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.rice.model;

import java.util.Date;

public class TestReport {
	
	public static final String FIELD_ID_ID = "id";
	public static final String FIELD_ID_LOTNO = "lotNo";
	public static final String FIELD_ID_PROJECT = "project";
	public static final String FIELD_ID_DATETIME = "dateTime";
	public static final String FIELD_ID_FQCOUNT = "fairQualityCount";
	public static final String FIELD_ID_FAULTCOUNT = "faultCount";
	public static final String FIELD_ID_SENSOR_SN = "firstSensorId";
	public static final String FIELD_ID_REPORTFILENAME = "reportFileName";

	private String id;
	private String project = "ADS";
	private String lotNo;
	private Date dateTime;
	private int fairQualityCount;
	private int faultCount;
	private String firstSensorId;
	private String reportFileName;
	
	private SensorReport[] sensorReports;

	public int getTotalTestCount() {
		return fairQualityCount + faultCount;
	}
	
	public SensorReport[] getSensorReports() {
		return sensorReports;
	}
	public void setSensorReports(SensorReport[] sensorReports) {
		this.sensorReports = sensorReports;
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
	public String getFirstSensorId() {
		return firstSensorId;
	}
	public void setFirstSensorId(String firstSensorId) {
		this.firstSensorId = firstSensorId;
	}
	public String getReportFileName() {
		return reportFileName;
	}
	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}
	
}
