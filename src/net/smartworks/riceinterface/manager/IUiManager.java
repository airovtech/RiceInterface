/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.riceinterface.manager;

import java.util.Date;

import net.smartworks.common.Data;
import net.smartworks.riceinterface.model.SummaryReport;
import net.smartworks.riceinterface.model.SummaryReportPop;
import net.smartworks.riceinterface.model.TestReport;
import net.smartworks.riceinterface.model.TestReportCond;

public interface IUiManager {

	//검사별 리스트 사이즈
	public int getTestReportSize(TestReportCond cond) throws Exception;
	//검사별 리스트
	public TestReport[] getTestReports(TestReportCond cond) throws Exception;
	
	//검사별 상세
	public TestReport getTestReportWithSensorReports(String testReportId) throws Exception;
	
	//기간별 리스트
	//selector : byDay, byWeek, byMonth, byYear
	public SummaryReport[] getSummaryReports(Date fromDate, Date toDate, String selector) throws Exception;
	
	//fromDate, toDate, grouping 
	//마지막 파라미터에 위 getSummaryReports 결과값인 검사일 데이터를 입력한다(예: 2014년1월1월)
	//팝업데이터를 다시 취합하기 위하여 해당 정보들이 필요
	public SummaryReportPop getSummaryReportPop(Date fromDate, Date toDate, String selector, String selectTestDate) throws Exception;
	
	//차트 데이터
	public Data getLineChartReportData(Date fromDate, Date toDate, String selector) throws Exception;
	public Data getBarChartReportData(Date fromDate, Date toDate, String selector) throws Exception;
	
}
