/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 10.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.rice.manager.impl;

import java.util.ArrayList;
import java.util.List;

import net.smartworks.factory.SessionFactory;
import net.smartworks.rice.manager.IRptManager;
import net.smartworks.rice.model.FtpHistory;
import net.smartworks.rice.model.FtpHistoryCond;
import net.smartworks.rice.model.SensorReport;
import net.smartworks.rice.model.TestReport;

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
}
