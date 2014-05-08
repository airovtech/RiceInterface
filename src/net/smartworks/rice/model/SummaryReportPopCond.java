/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 22.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.rice.model;

import java.util.Date;

import net.smartworks.common.Cond;
import net.smartworks.util.DateUtil;

public class SummaryReportPopCond extends Cond {
	
	public static final String CHARTTYPEALL = "ALL";
	public static final String CHARTTYPEFAULT = "FAULT";
	
	private Date fromDate;
	private Date toDate;
	private String selector;
	private String selectTestDate;

	public Date getFromDate() {
		return DateUtil.toFromDate(this.fromDate, DateUtil.CYCLE_DAY);
		//return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return DateUtil.toToDate(this.toDate, DateUtil.CYCLE_DAY);
		//return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getSelector() {
		return selector;
	}
	public void setSelector(String selector) {
		this.selector = selector;
	}
	public String getSelectTestDate() {
		return selectTestDate;
	}
	public void setSelectTestDate(String selectTestDate) {
		this.selectTestDate = selectTestDate;
	}
	public String getFairQualityCode() {
		return SensorReport.fairQualityCode;
	}
}
