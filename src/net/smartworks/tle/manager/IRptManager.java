/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 10.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.tle.manager;

import java.util.List;

import net.smartworks.tle.model.FtpHistory;
import net.smartworks.tle.model.FtpHistoryCond;
import net.smartworks.tle.model.SensorReport;
import net.smartworks.tle.model.TestReport;

public interface IRptManager {
	
	//file 전송 이력을 저장한다
	public FtpHistory setFtpHistory(FtpHistory history) throws Exception;
	//file 전송 이력을 가져온다
	public List getFtpHistory(FtpHistoryCond cond) throws Exception;
	
	//public TestReport getTestReport(String id) throws Exception;
	
	//Scheduler 및 Ftp 가 데이터를 취합하여 데이터베이스에 입력하는 메소드
	public TestReport setTestReport(TestReport report) throws Exception;
	//마지막 ID 값을 가져옴
	public int getLastId();
	public void setSensorReport(List<SensorReport> sensorDataList);
	public List<SensorReport> getSensorDataList(int id);
	public List<Object> getSensorReport2();
	//public void removeTestReport(String id) throws Exception;
	//public long getTestReportSize(TestReportCond cond) throws Exception;
	//public TestReport[] getTestReports(TestReportCond cond) throws Exception;

	//public SensorReport getSensorReport(String id) throws Exception;
	//public SensorReport setSensorReport(SensorReport report) throws Exception;
	//public void removeSensorReport(String id) throws Exception;
	//public long getSensorReportSize(SensorReportCond cond) throws Exception;
	//public SensorReport[] getSensorReports(SensorReportCond cond) throws Exception;
}
