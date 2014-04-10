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
	
	//검사별 화면 리스트
	public TestReport[] getTestReports(TestReportCond cond) throws Exception;
	
	//검사별 항목 상세 화면
	public TestReport getTestReportWithSensorReports(String testReportId) throws Exception;
	
	//기간별 검사 리스트
	//selector : byDay, byWeek, byMonth, byYear
	public SummaryReport[] getSummaryReports(Date fromDate, Date toDate, String selector) throws Exception;
	
	//기간별 검사 팝업(선택 항목의 양품대불량수량비율, 불량수량 코드 비율)
	//fromDate, toDate, grouping 은 "기간별 검사 리스트"의 조건과 동일한 조건을 넘겨주며
	//리스트에 표시된 검사일을 마지막 파라미터로넘긴다(예:2013년1주)
	public SummaryReportPop getSummaryReportPop(Date fromDate, Date toDate, String selector, String testDate) throws Exception;
	
	//차트데이터
	public Data getLineChartReportData(Date fromDate, Date toDate, String selector) throws Exception;
	public Data getBarChartReportData(Date fromDate, Date toDate, String selector) throws Exception;
	
}
