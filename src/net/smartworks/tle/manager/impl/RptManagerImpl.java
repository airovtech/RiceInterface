/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 10.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.tle.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.smartworks.factory.SessionFactory;
import net.smartworks.factory.SessionFactory1;
import net.smartworks.tle.manager.IRptManager;
import net.smartworks.tle.model.FtpHistory;
import net.smartworks.tle.model.FtpHistoryCond;
import net.smartworks.tle.model.SensorReport;
import net.smartworks.tle.model.TestReport;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class RptManagerImpl implements IRptManager {

	public FtpHistory setFtpHistory(FtpHistory history) throws Exception {
		if (history == null)
			return null;
		SqlSession session = null;
		try {
			SqlSessionFactory factory = SessionFactory.getInstance().getSqlSessionFactory();
			session = factory.openSession(ExecutorType.BATCH);
			session.commit(false);
			
			session.insert("setFtpHistory", history);

			session.commit();
			
			return history;
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
	}
	
	public List getFtpHistory(FtpHistoryCond cond) throws Exception {
		SqlSession session = null;
		try {
			SqlSessionFactory factory = SessionFactory.getInstance().getSqlSessionFactory();
			session = factory.openSession();
			List<FtpHistory> ftpHistorys = session.selectList("getFtpHistory", cond);
			if (ftpHistorys != null && ftpHistorys.size() != 0) {
				List result = new ArrayList();
				for (FtpHistory history : ftpHistorys) {
					result.add(history.getFileName());
				}
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
	public TestReport setTestReport(TestReport report) throws Exception {

		if (report == null)
			return null;
		SqlSession session = null;
		try {
			SqlSessionFactory factory = SessionFactory.getInstance().getSqlSessionFactory();
			session = factory.openSession(ExecutorType.BATCH);
			session.commit(false);
			
			session.insert("setTestReport", report);
			if (report.getSensorReports() != null) {
				for (SensorReport sr : report.getSensorReports()) {
					session.insert("setSensorReport", sr);
				}
			}
			session.commit();
			
			return report;
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
	}
	

	@Override
	public int getLastId() {
		SqlSession session = null;
		int LastId = 0;
		try {
			SqlSessionFactory factory = SessionFactory.getInstance().getSqlSessionFactory();
			session = factory.openSession(ExecutorType.BATCH);
			session.commit(false);
			LastId = session.selectOne("getLastId");
			return LastId;
			
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.commit();
			if (session != null)
				session.close();
		}
		return LastId;
	}

	@Override
	public void setSensorReport(List<SensorReport> sensorDataList) {
		SqlSession session = null;
		try {
			SqlSessionFactory factory = SessionFactory.getInstance().getSqlSessionFactory();
			session = factory.openSession(ExecutorType.BATCH);
//			IRptManager iRptManager = ((SqlSession) factory).getMapper(IRptManager.class);
			
			for (SensorReport sr : sensorDataList) {
				session.insert("setSensorReport", sr);
//				iRptManager.setSensorReport(sr);
			}
			
			
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.commit();
			if (session != null)
				
				session.close();
		}
	}

	@Override
	public List<SensorReport> getSensorDataList(int id) {

		if (id == 0)
			return null;
		SqlSession session = null;
		List<SensorReport> sensorReportList = null;
		try {
			SqlSessionFactory factory = SessionFactory1.getInstance().getSqlSessionFactory();
			session = factory.openSession(ExecutorType.BATCH);
			session.commit(false);
			sensorReportList = session.selectList("getSensorReport",id);
				
			
			session.commit();
			
			return sensorReportList; 
		} catch (Exception e) {
			session.rollback();
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			if (session != null)
				session.close();
			return sensorReportList;
		}
	}

	@Override
	public List<Object> getSensorReport2() {
		SqlSession session = null;
		List<Object> list = null;
		try {
			SqlSessionFactory factory = SessionFactory1.getInstance().getSqlSessionFactory();
			session = factory.openSession(ExecutorType.BATCH);
			session.commit(false);
			
			list = session.selectList("getSensorReport2");
			session.commit();
			
			return list;
		} catch (Exception e) {
			session.rollback();
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			if (session != null) {
				session.close();
			}
			return list;
		}
	}

}
