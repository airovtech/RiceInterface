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
	
	//�˻纰 ȭ�� ����Ʈ
	public TestReport[] getTestReports(TestReportCond cond) throws Exception;
	
	//�˻纰 �׸� �� ȭ��
	public TestReport getTestReportWithSensorReports(String testReportId) throws Exception;
	
	//�Ⱓ�� �˻� ����Ʈ
	//selector : byDay, byWeek, byMonth, byYear
	public SummaryReport[] getSummaryReports(Date fromDate, Date toDate, String selector) throws Exception;
	
	//�Ⱓ�� �˻� �˾�(���� �׸��� ��ǰ��ҷ���������, �ҷ����� �ڵ� ����)
	//fromDate, toDate, grouping �� "�Ⱓ�� �˻� ����Ʈ"�� ���ǰ� ������ ������ �Ѱ��ָ�
	//����Ʈ�� ǥ�õ� �˻����� ������ �Ķ���ͷγѱ��(��:2013��1��)
	public SummaryReportPop getSummaryReportPop(Date fromDate, Date toDate, String selector, String testDate) throws Exception;
	
	//��Ʈ������
	public Data getLineChartReportData(Date fromDate, Date toDate, String selector) throws Exception;
	public Data getBarChartReportData(Date fromDate, Date toDate, String selector) throws Exception;
	
}
