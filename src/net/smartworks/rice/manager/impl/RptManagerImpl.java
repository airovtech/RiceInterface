/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 10.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.rice.manager.impl;

import net.smartworks.factory.SessionFactory;
import net.smartworks.rice.manager.IRptManager;
import net.smartworks.rice.model.SensorReport;
import net.smartworks.rice.model.TestReport;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class RptManagerImpl implements IRptManager {

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
