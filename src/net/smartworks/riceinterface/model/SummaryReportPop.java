/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.riceinterface.model;

import java.util.Map;

public class SummaryReportPop {

	public int totalFairQualityCount;
	public int totalFaultCount;
	//key : 불량 코드 , value : 코드 갯수
	public Map<String, Integer> faultCodeCountMap;
	
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
	public Map getFaultCodeCountMap() {
		return faultCodeCountMap;
	}
	public void setFaultCodeCountMap(Map faultCodeCountMap) {
		this.faultCodeCountMap = faultCodeCountMap;
	}
}
