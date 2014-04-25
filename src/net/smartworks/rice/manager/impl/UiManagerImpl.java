/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.rice.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.smartworks.common.Data;
import net.smartworks.factory.SessionFactory;
import net.smartworks.rice.manager.IUiManager;
import net.smartworks.rice.model.SensorReport;
import net.smartworks.rice.model.SensorReportCond;
import net.smartworks.rice.model.SummaryLineChartData;
import net.smartworks.rice.model.SummaryReport;
import net.smartworks.rice.model.SummaryReportCond;
import net.smartworks.rice.model.SummaryReportPop;
import net.smartworks.rice.model.SummaryReportPopCond;
import net.smartworks.rice.model.SummaryReportPopRSet;
import net.smartworks.rice.model.TestReport;
import net.smartworks.rice.model.TestReportCond;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UiManagerImpl implements IUiManager {

	public UiManagerImpl() {
		//org.apache.ibatis.logging.LogFactory.useLog4JLogging();
	}
	@Override
	public int getTestReportSize(TestReportCond cond) throws Exception {
		SqlSession session = null;
		try {
			SqlSessionFactory factory = SessionFactory.getInstance().getSqlSessionFactory();
			session = factory.openSession();
			//TEST 중
			int totalSize = session.selectOne("getTestReportSize", cond);
			
			return totalSize;
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
	}
	@Override
	public TestReport[] getTestReports(TestReportCond cond) throws Exception {
		SqlSession session = null;
		try {
			SqlSessionFactory factory = SessionFactory.getInstance().getSqlSessionFactory();
			session = factory.openSession();
			
			RowBounds Rb = null;
			if (cond.getPageSize() != -1) {
				int pageNo = cond.getPageNo();
				int pageSize = cond.getPageSize();
				int startPage = (pageNo - 1) * pageSize;
				Rb = new RowBounds(startPage, pageSize);
			} else {
				Rb = new RowBounds();
			}
			
			List<TestReport> reports = session.selectList("getTestReports", cond, Rb);
			if (reports != null) {
				TestReport[] result = new TestReport[reports.size()];
				reports.toArray(result);
				return result;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public TestReport getTestReportWithSensorReports(String testReportId) throws Exception {
		SqlSession session = null;
		try {
			SqlSessionFactory factory = SessionFactory.getInstance().getSqlSessionFactory();
			session = factory.openSession();
			TestReportCond testCond = new TestReportCond();
			testCond.setId(testReportId);
			TestReport report = session.selectOne("getTestReports", testCond);
			if (report == null)
				return null;
			
			SensorReportCond sensorCond = new SensorReportCond();
			sensorCond.setTestReportId(testReportId);
			List<SensorReport> sensorReports = session.selectList("getSensorReports", sensorCond);
			if (sensorReports != null) {
				SensorReport[] result = new SensorReport[sensorReports.size()];
				sensorReports.toArray(result);
				report.setSensorReports(result);
			} 
			return report;
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public SummaryReport[] getSummaryReports(Date fromDate, Date toDate, String selector) throws Exception {
		SqlSession session = null;
		try {
			SqlSessionFactory factory = SessionFactory.getInstance().getSqlSessionFactory();
			session = factory.openSession();
			SummaryReportCond reportCond = new SummaryReportCond();
			reportCond.setSelector(selector);
			reportCond.setFromDate(fromDate);
			reportCond.setToDate(toDate);
			
			List<SummaryReport> summaryReports = session.selectList("getSummaryReport", reportCond);
			SummaryReport[] result = null;
			if (summaryReports != null) {
				result = new SummaryReport[summaryReports.size()];
				summaryReports.toArray(result);
			} 
			return result;
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public SummaryReportPop getSummaryReportPop(Date fromDate, Date toDate, String selector, String selectTestDate) throws Exception {
		SqlSession session = null;
		try {
			SqlSessionFactory factory = SessionFactory.getInstance().getSqlSessionFactory();
			session = factory.openSession();
			SummaryReportPopCond reportCond = new SummaryReportPopCond();
			reportCond.setSelector(selector);
			reportCond.setFromDate(fromDate);
			reportCond.setToDate(toDate);
			reportCond.setSelectTestDate(selectTestDate);
			
			List<SummaryReportPopRSet> summaryReportRs = session.selectList("getSummaryReportPop", reportCond);
			SummaryReportPop result = new SummaryReportPop();
			if (summaryReportRs != null && summaryReportRs.size() != 0) {
				Map codeCountMap = new HashMap();
				int fairCount = 0;
				int faultCount = 0;
				for (int i = 0; i < summaryReportRs.size(); i++) {
					SummaryReportPopRSet rs = summaryReportRs.get(i);
					String codeName = rs.getDecisionCode();
					if (codeName.equalsIgnoreCase(SensorReport.fairQualityCode)) {
						fairCount = fairCount + rs.getCodeCount();
					} else {
						faultCount = faultCount + rs.getCodeCount();
					}
					codeCountMap.put(codeName, rs.getCodeCount());
				}
				result.setFaultCodeCountMap(codeCountMap);
				result.setTotalFairQualityCount(fairCount);
				result.setTotalFaultCount(faultCount);
			}
			return result;
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public Data getLineChartReportData(Date fromDate, Date toDate, String selector) throws Exception {
		//기간별 생산(양품)수량
		String xFieldName = "검사일";
		String yValueName = "갯수";
		
		SqlSession session = null;
		try {
			SqlSessionFactory factory = SessionFactory.getInstance().getSqlSessionFactory();
			session = factory.openSession();
			SummaryReportPopCond reportCond = new SummaryReportPopCond();
			reportCond.setSelector(selector);
			reportCond.setFromDate(fromDate);
			reportCond.setToDate(toDate);
			
			List<SummaryLineChartData> summaryCharts = session.selectList("getLineChartReportData", reportCond);
			
			Data chartData = new Data();
			chartData.setGroupNames(new String[]{yValueName});
			chartData.setxFieldName(xFieldName);
			chartData.setyValueName(yValueName);
			
			if (summaryCharts != null) {
				List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
				for (SummaryLineChartData data : summaryCharts) {
					Map valueMap = new HashMap();
				
					String testDate = data.getTestDate();
					int totalFairQualityCount = data.getTotalFairQualityCount();
					valueMap.put(xFieldName, testDate);
					valueMap.put(yValueName, totalFairQualityCount);
					
					values.add(valueMap);
				}
				chartData.setValues(values);
			}
			return chartData;
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public Data getBarChartReportData(Date fromDate, Date toDate, String selector) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
