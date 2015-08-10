/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 25.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.rice.model;

public class SummaryBarChartData {

	private String testDate;
	private int totalFairQualityCount;
	private int totalFaultCount;

	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public int getTotalFairQualityCount() {
		return totalFairQualityCount;
	}
	public void setTotalFairQualityCount(int totalFairQualityCount) {
		this.totalFairQualityCount = totalFairQualityCount;
	}
	public int getTotalFaultCount() {
		return totalFaultCount;
	}
	public void setTotalFaultCount(int totalFaultCount) {
		this.totalFaultCount = totalFaultCount;
	}
}
