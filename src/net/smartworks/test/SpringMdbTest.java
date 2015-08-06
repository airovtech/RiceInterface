/* 
 * $Id$
 * created by    : YJ
 * creation-date : 2015. 7. 13.
 * =========================================================
 * Copyright (c) 2015 SmartWorks, Inc. All rights reserved.
 */

package net.smartworks.test;

import java.util.ArrayList;
import java.util.List;

import net.smartworks.factory.ManagerFactory;

public class SpringMdbTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			int lastId = ManagerFactory.getInstance().getRptManager().getLastId();
//			List<SensorReport> sensorDataList = new ArrayList<SensorReport1>();
//			
			List<Object> list = new ArrayList<Object>();
			list = ManagerFactory.getInstance().getRptManager().getSensorReport2();
			for(Object ob : list) {
				System.out.println(ob);
				
			}
			
			/*sensorDataList = ManagerFactory.getInstance().getRptManager().getSensorDataList(63000);
 			if(!sensorDataList.isEmpty()) {
	 			for(SensorReport sr : sensorDataList) {
	 				System.out.println(sr.getQrCode());
	 			}
	 		}*/
 			
 			
 //			if(!sensorDataList.isEmpty()) {
//				ManagerFactory.getInstance().getRptManager().setSensorReport(sensorDataList);
//			}
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
