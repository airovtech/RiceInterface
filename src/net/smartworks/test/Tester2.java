/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.test;

import net.smartworks.factory.ManagerFactory;
import net.smartworks.riceinterface.manager.IUiManager;
import net.smartworks.riceinterface.model.TestReport;
import net.smartworks.riceinterface.model.TestReportCond;


public class Tester2 {

	public static void main(String[] args) {

		//select
		try {
			IUiManager mgr = ManagerFactory.getInstance().getUiManager();
			TestReport rep = mgr.getTestReportWithSensorReports("id_new_jdbc_124");
			System.out.println(rep.getLotNo());
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*try {
			IUiManager mgr = ManagerFactory.getInstance().getUiManager();
			TestReportCond cond = new TestReportCond();
			cond.setId("id_new_jdbc_124");
			cond.setLotNo("MMEES124");
			TestReport[] reps = mgr.getTestReports(cond);
			for (TestReport rp : reps) {
				System.out.println(rp.getLotNo());
			}
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		//set
		/*try {
			
			TestReport tr = new TestReport();
			tr.setId("id_new_jdbc_124");
			tr.setProject("ADS");
			tr.setLotNo("MMEES124");
			tr.setDateTime(new Date());
			tr.setFairQualityCount(30);
			tr.setFaultCount(70);
			tr.setSensorSerialNo("sensor_001");
			tr.setReportFileName("Report_123123.txt");
			
			
			SensorReport[] sts = new SensorReport[3];
			
			SensorReport sr = new SensorReport();
			sr.setId("sensorId1");
			sr.setProject("ADS");
			sr.setTestReportId(tr.getId());
			sr.setSensorNo("sensor-no1");
			sr.setLotNo(tr.getLotNo());
			sr.setDateTime(tr.getDateTime());
			sr.setIndoorHumidity("12.23");
			sr.setGlassTemperature("23.22");
			sr.setIndoorTemperature("11.23");
			sr.setDecisionCode("001");
			sr.setSerialNo("sensor_oo1");
			sts[0] = sr;
			
			SensorReport sr2 = new SensorReport();
			sr2.setId("sensorId2");
			sr2.setProject("ADS");
			sr2.setTestReportId(tr.getId());
			sr2.setSensorNo("sensor-no2");
			sr2.setLotNo(tr.getLotNo());
			sr2.setDateTime(tr.getDateTime());
			sr2.setIndoorHumidity("12.23");
			sr2.setGlassTemperature("23.22");
			sr2.setIndoorTemperature("11.23");
			sr2.setDecisionCode("001");
			sr2.setSerialNo("sensor_oo2");
			sts[1] = sr2;
			
			SensorReport sr3 = new SensorReport();
			sr3.setId("sensorId3");
			sr3.setProject("ADS");
			sr3.setTestReportId(tr.getId());
			sr3.setSensorNo("sensor-no3");
			sr3.setLotNo(tr.getLotNo());
			sr3.setDateTime(tr.getDateTime());
			sr3.setIndoorHumidity("12.23");
			sr3.setGlassTemperature("23.22");
			sr3.setIndoorTemperature("11.23");
			sr3.setDecisionCode("001");
			sr3.setSerialNo("sensor_oo3");
			sts[2] = sr3;
			
			tr.setSensorReports(sts);
			
			ManagerFactory.getInstance().getRptManager().setTestReport(tr);
			
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
