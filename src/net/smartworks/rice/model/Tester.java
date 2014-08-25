/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 5. 8.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.rice.model;

import java.util.Calendar;
import java.util.Date;

import net.smartworks.util.DateUtil;
import net.smartworks.util.SmartUtil;

public class Tester {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		
		
		SummaryReportCond cond = new SummaryReportCond();
		
//		Calendar cal = Calendar.getInstance();
//		
//		cal.set(Calendar.YEAR, 2014);
//		cal.set(Calendar.MONTH, 3);
//		cal.set(Calendar.DATE, 25);
//		
//		cal.set(Calendar.HOUR, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		cal.set(Calendar.MILLISECOND, 0);
//		
//		
		System.out.println(SmartUtil.convertDateStringToDate("2014.04.25"));
//		
		cond.setToDate(SmartUtil.convertDateStringToDate("2014.04.25"));
		
		System.out.println(cond.getToDate());
		
		
	}

}
