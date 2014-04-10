/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.riceinterface.manager.impl;

import java.util.Date;
import java.util.List;

import net.smartworks.common.Data;
import net.smartworks.factory.SessionFactory;
import net.smartworks.riceinterface.manager.IUiManager;
import net.smartworks.riceinterface.model.SensorReport;
import net.smartworks.riceinterface.model.SensorReportCond;
import net.smartworks.riceinterface.model.SummaryReport;
import net.smartworks.riceinterface.model.SummaryReportPop;
import net.smartworks.riceinterface.model.TestReport;
import net.smartworks.riceinterface.model.TestReportCond;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UiManagerImpl implements IUiManager {

	public UiManagerImpl() {
		//org.apache.ibatis.logging.LogFactory.useLog4JLogging();
	}
	
	@Override
	public TestReport[] getTestReports(TestReportCond cond) throws Exception {
		SqlSession session = null;
		try {
			SqlSessionFactory factory = SessionFactory.getInstance().getSqlSessionFactory();
			session = factory.openSession();
			List<TestReport> reports = session.selectList("getTestReports", cond);
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

		
		return null;
	}

	@Override
	public SummaryReportPop getSummaryReportPop(Date fromDate, Date toDate, String selector, String testDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data getLineChartReportData(Date fromDate, Date toDate, String selector) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data getBarChartReportData(Date fromDate, Date toDate, String selector) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
