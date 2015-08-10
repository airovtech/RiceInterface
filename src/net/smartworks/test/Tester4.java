/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.test;

import java.util.Date;

import net.smartworks.common.Data;
import net.smartworks.factory.ManagerFactory;
import net.smartworks.rice.manager.IUiManager;
import net.smartworks.rice.model.TestReport;
import net.smartworks.rice.model.TestReportCond;

public class Tester4 {

	public static void main(String[] args) {
		try {
			IUiManager mgr = ManagerFactory.getInstance().getUiManager();
			
			
			TestReportCond cond = new TestReportCond();
			cond.setLotNoLike("G4");
			
			TestReport[] res = mgr.getTestReports(cond);
			
			for (int i = 0; i < res.length; i++) {
				TestReport re = res[i];
				System.out.println(re.getLotNo());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
