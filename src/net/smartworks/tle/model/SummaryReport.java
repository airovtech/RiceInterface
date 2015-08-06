/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.tle.model;

public class SummaryReport {
	
	public String testDate;
	public int totalTestCount = -1;
	public int totalFairQualityCount = -1;
	public int totalFaultCount = -1;
	public double faultPercent = -1;
	
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public int getTotalTestCount() {
		return totalTestCount;
	}
	public void setTotalTestCount(int totalTestCount) {
		this.totalTestCount = totalTestCount;
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
	public double getFaultPercent() {
		if (this.totalTestCount != -1 && this.totalFaultCount != -1) {
			return (((double)totalFaultCount / totalTestCount) * 100);
		} else {
			return 0;
		}
		//return faultPercent;
	}
	//public void setFaultPercent(int faultPercent) {
	//	this.faultPercent = faultPercent;
	//}
}
