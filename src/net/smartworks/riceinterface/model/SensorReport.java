/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.riceinterface.model;

import java.util.Date;

public class SensorReport {

	public static final String fairQualityCode = "000";
	
	private String id;
	private String project = "ADS";
	private String testReportId;
	private String sensorNo;
	private String lotNo;
	private Date dateTime;
	private String indoorTemperature;
	private String glassTemperature;
	private String indoorHumidity;
	private String decisionCode;
	private String serialNo;
	
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
	public String getTestReportId() {
		return testReportId;
	}
	public void setTestReportId(String testReportId) {
		this.testReportId = testReportId;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getSensorNo() {
		return sensorNo;
	}
	public void setSensorNo(String sensorNo) {
		this.sensorNo = sensorNo;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getIndoorTemperature() {
		return indoorTemperature;
	}
	public void setIndoorTemperature(String indoorTemperature) {
		this.indoorTemperature = indoorTemperature;
	}
	public String getGlassTemperature() {
		return glassTemperature;
	}
	public void setGlassTemperature(String glassTemperature) {
		this.glassTemperature = glassTemperature;
	}
	public String getIndoorHumidity() {
		return indoorHumidity;
	}
	public void setIndoorHumidity(String indoorHumidity) {
		this.indoorHumidity = indoorHumidity;
	}
	public String getDecisionCode() {
		return decisionCode;
	}
	public void setDecisionCode(String decisionCode) {
		this.decisionCode = decisionCode;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
}
