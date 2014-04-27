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

public class SummaryReportCond extends Cond {
	
	public static final String SELECTOR_DAILY = "byDay";
	public static final String SELECTOR_WEEKLY = "byWeek";
	public static final String SELECTOR_MONTHLY = "byMonth";
	public static final String SELECTOR_YEARLY = "byYear";
	
	private Date fromDate;
	private Date toDate;
	
	private String selector;

	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
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
}
