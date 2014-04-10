/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 10.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.riceinterface.manager;

import net.smartworks.riceinterface.model.TestReport;

public interface IRptManager {

	//public TestReport getTestReport(String id) throws Exception;
	
	//Schedule 및 Ftp 모듈을 통해 파싱된 데이터를 저장하는 메소드
	public TestReport setTestReport(TestReport report) throws Exception;

	//public void removeTestReport(String id) throws Exception;
	//public long getTestReportSize(TestReportCond cond) throws Exception;
	//public TestReport[] getTestReports(TestReportCond cond) throws Exception;

	//public SensorReport getSensorReport(String id) throws Exception;
	//public SensorReport setSensorReport(SensorReport report) throws Exception;
	//public void removeSensorReport(String id) throws Exception;
	//public long getSensorReportSize(SensorReportCond cond) throws Exception;
	//public SensorReport[] getSensorReports(SensorReportCond cond) throws Exception;
}
