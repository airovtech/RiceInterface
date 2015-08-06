/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.tle.model;

import java.util.Date;

public class SensorReport {

	public static final String fairQualityCode = "000";
	
	private String date;
	private Date dateTime;
	private String customerName;
	private String productName;
	private String modelName;
	private String tester;
	private String qrCode;
	private String dataMatrix;
	private String lotNo;
	private String humidity;
	private String decisionCode;
	private int idx;
	private String id;
	private String testReportId;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getTester() {
		return tester;
	}
	public void setTester(String tester) {
		this.tester = tester;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getDataMatrix() {
		return dataMatrix;
	}
	public void setDataMatrix(String dataMatrix) {
		this.dataMatrix = dataMatrix;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getDecisionCode() {
		return decisionCode;
	}
	public void setDecisionCode(String decisionCode) {
		this.decisionCode = decisionCode;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public static String getFairqualitycode() {
		return fairQualityCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTestReportId() {
		return testReportId;
	}
	public void setTestReportId(String testReportId) {
		this.testReportId = testReportId;
	}
}
