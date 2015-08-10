/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 22.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.rice.model;

public class SummaryReportPopRSet {
	
	private String decisionCode;
	private String decisionCodeDesc;
	private int codeCount;
	
	public String getDecisionCode() {
		return decisionCode;
	}
	public void setDecisionCode(String decisionCode) {
		this.decisionCode = decisionCode;
	}
	public int getCodeCount() {
		return codeCount;
	}
	public void setCodeCount(int codeCount) {
		this.codeCount = codeCount;
	}
	public String getDecisionCodeDesc() {
		return decisionCodeDesc;
	}
	public void setDecisionCodeDesc(String decisionCodeDesc) {
		this.decisionCodeDesc = decisionCodeDesc;
	}
}
