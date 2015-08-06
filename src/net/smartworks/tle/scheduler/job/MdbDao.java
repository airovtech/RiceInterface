/* 
 * $Id$
 * created by    : YJ
 * creation-date : 2015. 7. 15.
 * =========================================================
 * Copyright (c) 2015 SmartWorks, Inc. All rights reserved.
 */

package net.smartworks.tle.scheduler.job;

import java.sql.SQLException;
import java.util.List;

import net.smartworks.tle.model.TestReport;

public interface MdbDao {
	
	public void readMdb(int LastSensorId) throws SQLException;
	
	public TestReport readMdbByLotNo(String lotNo) throws SQLException;
	//새로 추가된 데이터 검색 , LOTNO 반환
	public List<String> SearchSensorReportById(int LastSensorId) throws SQLException;
	//양품 수
	public int fairQualityCount(String lotNo) throws SQLException;
	//불량 수
	public int faultCount(String lotNo) throws SQLException;
}
