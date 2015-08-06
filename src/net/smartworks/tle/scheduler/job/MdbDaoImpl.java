/* 
 * $Id$
 * created by    : YJ
 * creation-date : 2015. 7. 15.
 * =========================================================
 * Copyright (c) 2015 SmartWorks, Inc. All rights reserved.
 */

package net.smartworks.tle.scheduler.job;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.smartworks.tle.model.SensorReport;
import net.smartworks.tle.model.TestReport;
import net.smartworks.util.IdUtil;

public class MdbDaoImpl implements MdbDao{
	private Connection conn;
	private Statement stmt;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	
	
	/*public MdbDaoImpl() {
		DBConnection dbConn = new DBConnection();
		conn = dbConn.getConnection();
	}*/
	
	@Override
	public void readMdb(int LastSensorId) throws SQLException {
		/*DBConnection dbConn = new DBConnection();
		try{        
			
			
			*//***************************************
			getId : DB에 저장된 마지막 ID 가져와서 비교 
			if(ID < mdb ID) 면 가져와서 저장
			else 면 X
			***************************************//*
			SimpleDateFormat StringDate = new SimpleDateFormat("yyyy-MM-dd");
			String toDayStr = StringDate.format(new Date());
			System.out.println("today : " + toDayStr);
			toDayStr = "2015-07-01"; //TEST 날짜
//		    String sql = new String((" SELECT * FROM DBASE where 날짜 = '" + toDayStr + "'").getBytes("euc-kr"), "ISO-8859-1");
//		    String sql = new String((" SELECT * FROM DBASE where QRCODE = 'EKCR003'").getBytes("euc-kr"), "ISO-8859-1");
//			String sql = new String((" SELECT * FROM DBASE where 날짜 = '2015-07-01' ").getBytes("euc-kr"), "ISO-8859-1");
//			String sql = new String((" SELECT * FROM DBASE").getBytes("euc-kr"), "ISO-8859-1");
			int id = 66000;
//			String sql = new String((" SELECT * FROM DBASE WHERE id > '" + id + "' ORDER BY id ASC").getBytes("euc-kr"), "ISO-8859-1");
//			String sql = new String((" SELECT 날짜,시간,고객명,제품명,모델명,측정자,QRCODE,DATAMATRIX,LOTNO,측정습도,판정,ID FROM DBASE WHERE ID > 63000 ORDER BY ID ASC").getBytes("euc-kr"), "ISO-8859-1"); 
//			String sql =" SELECT 날짜,시간,고객명,제품명,모델명,측정자,QRCODE,DATAMATRIX,LOTNO,측정습도,판정,ID FROM DBASE WHERE ID > 63000 ORDER BY ID ASC "; 
			String sql = new String((" SELECT DISTINCT 날짜 FROM DBASE WHERE ID > " + LastSensorId + " ORDER BY ID ASC").getBytes("euc-kr"), "ISO-8859-1"); 
			
			System.out.println("ISO-8859-1 :	" + sql);
			String str = new String(sql.getBytes("ISO-8859-1"), "utf-8");
			System.out.println("utf-8 :	" + str);
			String str1 = new String(sql.getBytes("ISO-8859-1"), "euc-kr");
			System.out.println("euc-kr :	" + str1);
			
//			String sql = " SELECT * FROM DBASE WHERE id > " + id + " ORDER BY id ASC";
			
			
		    conn = dbConn.getConnection();  
		    psmt = conn.prepareStatement(sql);        
		    rs = psmt.executeQuery();
		    
		    List<SensorReport1> sensorDataList = new ArrayList<SensorReport1>();
//		    sensorDataList.isEmpty();
		    
//		    rs이 널이 아니면 
		    if(!rs.wasNull()) {
			    while (rs.next()) {
	
			        SensorReport1 sr = new SensorReport1();
					
					sr.setDate(rs.getString(1));
					sr.setDateTime(new String(rs.getString(2).getBytes("ISO-8859-1"), "euc-kr"));
					sr.setCustomerName(rs.getString(3));
					sr.setProductName(rs.getString(4));
					sr.setModelName(rs.getString(5));
					sr.setTester(new String(rs.getString(6).getBytes("ISO-8859-1"), "euc-kr"));
					sr.setQrCode(rs.getString(7));
					sr.setDataMatrix(rs.getString(8));
					sr.setLotNo(rs.getString(9));
					sr.setHumidity(String.format("%.2f",rs.getFloat(10)));
					sr.setDecisionCode(new String(rs.getString(11).getBytes("ISO-8859-1"), "euc-kr"));
					sr.setIdx(rs.getInt(12)); 
				
					
					System.out.println("날짜 : " + sr.getDate() + ", 시간 : " 
					+ sr.getDateTime() + ", 고객명 : " + sr.getCustomerName() + ", 제품명 : " + sr.getProductName() 
					+ ", 모델명 : " + sr.getModelName() + ", 측정자 : " + sr.getTester() + ", QRCODE : " + sr.getQrCode()
					+ ", Datamatrix : " + sr.getDataMatrix() + ", LotNo : " + sr.getHumidity() + ", 습도 : " + sr.getHumidity()
					+ ", 판정 : " + sr.getDecisionCode() + ", ID : " + sr.getId());
					sensorDataList.add(sr);
			    }  
		    }
		    
		    dbConn.disConnection(rs, psmt, conn);
		}catch(Exception e){
			if(rs != null) { rs.close(); }
			if(psmt != null) { psmt.close(); }
			if(conn != null) { conn.close(); }

		    e.printStackTrace();
		}*/
	}

	@Override
	public TestReport readMdbByLotNo(String lotNo) throws SQLException {
		DBConnection dbConn = new DBConnection();
		List<SensorReport> sensorDataList = new ArrayList<SensorReport>();
		TestReport report = new TestReport();
		
		try{        
			
			String sql = new String((" SELECT 날짜,시간,고객명,제품명,모델명,측정자,QRCODE,DATAMATRIX,LOTNO,측정습도,판정,ID FROM DBASE WHERE LOTNO = '" + lotNo + "' ORDER BY ID ASC").getBytes("euc-kr"), "ISO-8859-1"); 
		    conn = dbConn.getConnection();  
		    psmt = conn.prepareStatement(sql);        
		    rs = psmt.executeQuery();
		    
		    if(!rs.wasNull()) {
			    while (rs.next()) {
			    	SensorReport sr = new SensorReport();
			    	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			    	
//			    	sr.setDateTime((new String(rs.getString(2).getBytes("ISO-8859-1"), "euc-kr")));
			    	String date = rs.getString(1);
			    	sr.setDate(date);
			    	String time = new String(rs.getString(2).getBytes("ISO-8859-1"), "euc-kr");
			    	
			    	StringBuffer strBuf = new StringBuffer();
			    	strBuf.append(date.substring(0,4));
					strBuf.append(date.substring(5,7));
					strBuf.append(date.substring(8,10));
					strBuf.append(time.substring(0,2));
					strBuf.append(time.substring(3,5));
					strBuf.append(time.substring(6,8));
					Date dateTime = sdf2.parse(strBuf.toString());
					sr.setDateTime(dateTime);
					
					sr.setCustomerName(rs.getString(3));
			    	sr.setProductName(rs.getString(4));
			    	sr.setModelName(rs.getString(5));
			    	sr.setTester((new String(rs.getString(6).getBytes("ISO-8859-1"), "euc-kr")));
			    	sr.setQrCode(rs.getString(7));
			    	sr.setDataMatrix(rs.getString(8));
			    	sr.setLotNo(rs.getString(9));
			    	sr.setHumidity(String.format("%.2f",rs.getFloat(10)));
			    	sr.setDecisionCode(new String(rs.getString(11).getBytes("ISO-8859-1"), "euc-kr"));
			    	sr.setIdx(rs.getInt(12)); 
			    	sr.setId("sen_"+IdUtil.getInstance().generate());
			    	sensorDataList.add(sr);
			    	
					
			    }
		    }
		    SensorReport[] sensorReports = new SensorReport[sensorDataList.size()];
			sensorDataList.toArray(sensorReports);				
			report.setSensorReports(sensorReports);
		    dbConn.disConnection(rs, psmt, conn);
		    return report;
		    
		}catch(Exception e){
			if(rs != null) { rs.close(); }
			if(psmt != null) { psmt.close(); }
			if(conn != null) { conn.close(); }

		    e.printStackTrace();
		}
		return report;
	}
	
	@Override
	public List<String> SearchSensorReportById(int lastId) throws SQLException {
		DBConnection dbConn = new DBConnection();
		List<String> list = new ArrayList<String>();
		try{        
			
			String sql = new String((" SELECT DISTINCT LOTNO FROM DBASE WHERE ID > " + lastId).getBytes("euc-kr"), "ISO-8859-1"); 
		    conn = dbConn.getConnection();  
		    psmt = conn.prepareStatement(sql);        
		    rs = psmt.executeQuery();
		    
		    if(!rs.wasNull()) {
			    while (rs.next()) {
			    	String lotNo = (new String(rs.getString(1).getBytes("ISO-8859-1"), "euc-kr"));
					list.add(lotNo);
			    }
		    }
		    
		    dbConn.disConnection(rs, psmt, conn);
		    return list;
		}catch(Exception e){
			if(rs != null) { rs.close(); }
			if(psmt != null) { psmt.close(); }
			if(conn != null) { conn.close(); }

		    e.printStackTrace();
		}
		return list;
	}

	@Override
	public int fairQualityCount(String lotNo) throws SQLException {
		DBConnection dbConn = new DBConnection();
		List<String> list = new ArrayList<String>();
		int fairQualityCount = 0;
		try{        
			
			String sql = new String((" SELECT COUNT(*) FROM DBASE WHERE LOTNO = '" + lotNo + "' AND 판정 = '양 품' ").getBytes("euc-kr"), "ISO-8859-1"); 
			
		    conn = dbConn.getConnection();  
		    psmt = conn.prepareStatement(sql);        
		    rs = psmt.executeQuery();
		    while (rs.next()) {
		    	fairQualityCount = rs.getInt(1);
		    }
		    
		    
		    
		    dbConn.disConnection(rs, psmt, conn);
		    return fairQualityCount;
		}catch(Exception e){
			if(rs != null) { rs.close(); }
			if(psmt != null) { psmt.close(); }
			if(conn != null) { conn.close(); }

		    e.printStackTrace();
		}
		return fairQualityCount;
	}

	@Override
	public int faultCount(String lotNo) throws SQLException {
		DBConnection dbConn = new DBConnection();
		List<String> list = new ArrayList<String>();
		int faultCount = 0;
		try{        
			
			String sql = new String((" SELECT COUNT(*) FROM DBASE WHERE LOTNO = '" + lotNo + "' AND 판정 = '불 량' ").getBytes("euc-kr"), "ISO-8859-1"); 
			
		    conn = dbConn.getConnection();  
		    psmt = conn.prepareStatement(sql);        
		    rs = psmt.executeQuery();
		    while (rs.next()) {
		    	faultCount = rs.getInt(1);
		    }
		    
		    
		    
		    dbConn.disConnection(rs, psmt, conn);
		    return faultCount;
		}catch(Exception e){
			if(rs != null) { rs.close(); }
			if(psmt != null) { psmt.close(); }
			if(conn != null) { conn.close(); }

		    e.printStackTrace();
		}
		return faultCount;
	}
	
	
}
