/* 
 * $Id$
 * created by    : YJ
 * creation-date : 2015. 7. 8.
 * =========================================================
 * Copyright (c) 2015 SmartWorks, Inc. All rights reserved.
 */

package net.smartworks.test;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.smartworks.factory.ManagerFactory;
import net.smartworks.tle.model.SensorReport;
import net.smartworks.tle.model.TestReport;
import net.smartworks.tle.scheduler.job.MdbDao;
import net.smartworks.tle.scheduler.job.MdbDaoImpl;
import net.smartworks.util.IdUtil;

public class DBConnectionTest {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		int lastId;
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
//		String toDayStr = sdf.format(new Date());
		try {
			/*DateFormat ddf = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat tdf = new SimpleDateFormat("HH시mm분ss초");
			Date date = new Date(ddf.parse(tr.getSensorReports()[0].getDate()).getTime());
			Date time = new Date(tdf.parse(tr.getSensorReports()[0].getDateTime()).getTime());
			Date dateTime = new Date(date.getTime() + time.getTime());*/
			
			
			//마지막 idx값 가져옴
			lastId = ManagerFactory.getInstance().getRptManager().getLastId();
//			lastId = 62778;
			MdbDao mdbDao = new MdbDaoImpl();
			//id로 보다 큰값인 데이터 서치 return lotNo
			List<String> listLotNo = mdbDao.SearchSensorReportById(lastId);
//			List<TestReport> listTr = new ArrayList<TestReport>();
//			Date date = sdf2.parse();	
			if(!listLotNo.isEmpty()) {
				for(String lotNo : listLotNo) { 
					
					
					StringBuffer strBuf = new StringBuffer();
					TestReport tr = new TestReport();
					tr = mdbDao.readMdbByLotNo(lotNo);
					tr.setLotNo(lotNo);
					tr.setId("rpt_"+IdUtil.getInstance().generate());
					
					
					
					tr.setDateTime(tr.getSensorReports()[0].getDateTime());
					
					tr.setFairQualityCount(mdbDao.fairQualityCount(lotNo));
					tr.setFaultCount(mdbDao.faultCount(lotNo));
					for(SensorReport sr : tr.getSensorReports()) {
						sr.setTestReportId(tr.getId());
					}
					
					ManagerFactory.getInstance().getRptManager().setTestReport(tr);
				}
			}
			else {
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		

		/*DBConnection dbConn = new DBConnection();
		Connection conn = null; 
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try{ */       


			/*//***************************************
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
//			String sql = new String((" SELECT 날짜 FROM DBASE WHERE ID > 63000 ORDER BY ID ASC").getBytes("euc-kr"), "ISO-8859-1"); 
			String sql = new String((" SELECT DISTINCT 날짜 FROM DBASE WHERE ID > 63000 ORDER BY ID ASC").getBytes("euc-kr"), "ISO-8859-1"); 

			System.out.println("ISO-8859-1 :	" + sql);
			String str = new String(sql.getBytes("ISO-8859-1"), "utf-8");
			System.out.println("utf-8 :	" + str);
			String str1 = new String(sql.getBytes("ISO-8859-1"), "euc-kr");
			System.out.println("euc-kr :	" + str1);

//			String sql = " SELECT * FROM DBASE WHERE id > " + id + " ORDER BY id ASC";


		    conn = dbConn.getConnection();  
		    psmt = conn.prepareStatement(sql);        

		    rs = psmt.executeQuery();

		    List<SensorReport> sensorDataList = new ArrayList<SensorReport>();
//		    sensorDataList.isEmpty();


//		    rs이 널이 아니면 
		    if(!rs.wasNull()) {
			    while (rs.next()) {

			        SensorReport sr = new SensorReport();
	//		        String username = new String(rs.getString("QRCODE").getBytes("8859_1"), "euc-kr");
	//		        String date = new String(rs.getString(1).getBytes("ISO-8859-1"), "euc-kr");

					String date = rs.getString(2);
					String dateTime = new String(rs.getString(3).getBytes("ISO-8859-1"), "euc-kr");
					String customerName = rs.getString(4);
					String productName = rs.getString(5);
					String modelName = rs.getString(6);
					String tester = new String(rs.getString(7).getBytes("ISO-8859-1"), "euc-kr");
					String qrCode = rs.getString(8);
					String dataMatrix = rs.getString(9);
					String lotNo = rs.getString(10);
					//String seniorNo5 = String.format("%1s",(new String(rs.getString(15).getBytes("ISO-8859-1"), "euc-kr")));
					String humidity = String.format("%.2f",rs.getFloat(15));
					String decisionCode = new String(rs.getString(16).getBytes("ISO-8859-1"), "euc-kr");
					String id = rs.getString(17);

					sr.setDate(rs.getString(2));
					sr.setDateTime(new String(rs.getString(3).getBytes("ISO-8859-1"), "euc-kr"));
					sr.setCustomerName(rs.getString(4));
					sr.setProductName(rs.getString(5));
					sr.setModelName(rs.getString(6));
					sr.setTester(new String(rs.getString(7).getBytes("ISO-8859-1"), "euc-kr"));
					sr.setQrCode(rs.getString(8));
					sr.setDataMatrix(rs.getString(9));
					sr.setLotNo(rs.getString(10));
					sr.setHumidity(String.format("%.2f",rs.getFloat(15)));
					sr.setDecisionCode(new String(rs.getString(16).getBytes("ISO-8859-1"), "euc-kr"));
					sr.setId(rs.getInt(17));

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
					sr.setId(rs.getInt(12));




					System.out.println("날짜 : " + sr.getDate() + ", 시간 : " 
					+ sr.getDateTime() + ", 고객명 : " + sr.getCustomerName() + ", 제품명 : " + sr.getProductName() 
					+ ", 모델명 : " + sr.getModelName() + ", 측정자 : " + sr.getTester() + ", QRCODE : " + sr.getQrCode()
					+ ", Datamatrix : " + sr.getDataMatrix() + ", LotNo : " + sr.getHumidity() + ", 습도 : " + sr.getHumidity()
					+ ", 판정 : " + sr.getDecisionCode() + ", ID : " + sr.getId());

					sensorDataList.add(sr);

			    }  
			    ManagerFactory.getInstance().getRptManager().setSensorReport(sensorDataList);
			    for(SensorReport sr : sensorDataList) {
			    	System.out.println("날짜 : " + sr.getDate() + ", 시간 : " 
							+ sr.getDateTime() + ", 고객명 : " + sr.getCustomerName() + ", 제품명 : " + sr.getProductName() 
							+ ", 모델명 : " + sr.getModelName() + ", 측정자 : " + sr.getTester() + ", QRCODE : " + sr.getQrCode()
							+ ", Datamatrix : " + sr.getDataMatrix() + ", LotNo : " + sr.getHumidity() + ", 습도 : " + sr.getHumidity()
							+ ", 판정 : " + sr.getDecisionCode() + ", ID : " + sr.getId());
			    }
			    
//			    IRptManager ui = new RptManagerImpl();
//				ui.setSensorReport(sensorDataList);
			    
			    
		    }
		    
		    dbConn.disConnection(rs, psmt, conn);
		}catch(Exception e){
			if(rs != null) { rs.close(); }
			if(psmt != null) { psmt.close(); }
			if(conn != null) { conn.close(); }

		    e.printStackTrace();
		    */
			
			
//-----------------------------------------------------------------------------------------------------------------------------
//distinct test

/*//			String sql = new String((" SELECT distinct 날짜 FROM DBASE WHERE ID > 63000 ORDER BY ID ASC").getBytes("euc-kr"), "ISO-8859-1"); 
			String sql = new String((" SELECT distinct 날짜 FROM DBASE WHERE ID > 60000 ").getBytes("euc-kr"), "ISO-8859-1"); 
//			String sql = new String((" SELECT 날짜,시간,고객명,제품명,모델명,측정자,QRCODE,DATAMATRIX,LOTNO,측정습도,판정,ID FROM DBASE WHERE ID > 63000 ORDER BY ID ASC").getBytes("euc-kr"), "ISO-8859-1"); 

			System.out.println(sql);
			
			conn = dbConn.getConnection();  
			psmt = conn.prepareStatement(sql);        
			rs = psmt.executeQuery();



			if(!rs.wasNull()) {
				while (rs.next()) {


					
					String date = (new String(rs.getString(1).getBytes("ISO-8859-1"), "euc-kr"));

					System.out.println(date);
					System.out.println("날짜 : " + sr.getDate() + ", 시간 : " 
						+ sr.getDateTime() + ", 고객명 : " + sr.getCustomerName() + ", 제품명 : " + sr.getProductName() 
						+ ", 모델명 : " + sr.getModelName() + ", 측정자 : " + sr.getTester() + ", QRCODE : " + sr.getQrCode()
						+ ", Datamatrix : " + sr.getDataMatrix() + ", LotNo : " + sr.getHumidity() + ", 습도 : " + sr.getHumidity()
						+ ", 판정 : " + sr.getDecisionCode() + ", ID : " + sr.getId());
				}  
			}

			dbConn.disConnection(rs, psmt, conn);
		}catch(Exception e){
			if(rs != null) { rs.close(); }
			if(psmt != null) { psmt.close(); }
			if(conn != null) { conn.close(); }

			e.printStackTrace();
		}
		//-----------------------------------------------------------------------------------------------------------------------------
		//distinct test
*/		

	}
}
