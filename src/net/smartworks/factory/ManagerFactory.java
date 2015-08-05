/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.factory;

import net.smartworks.tle.manager.IRptManager;
import net.smartworks.tle.manager.IUiManager;
import net.smartworks.tle.manager.impl.RptManagerImpl;
import net.smartworks.tle.manager.impl.UiManagerImpl;

public class ManagerFactory {

	private static ManagerFactory managerFactory;
	private static IUiManager uiManager;
	private static IRptManager rptManager;
	
	public static ManagerFactory getInstance() {
		if (managerFactory != null) {
			return managerFactory;
		} else {
			managerFactory = new ManagerFactory();
			return managerFactory;
		}
	}
	public IUiManager getUiManager() throws Exception {
		if (uiManager != null) {
			return uiManager;
		} else {
			uiManager = new UiManagerImpl();
			return uiManager;
		}
	}
	public IRptManager getRptManager() throws Exception {
		if (rptManager != null) {
			return rptManager;
		} else {
			rptManager = new RptManagerImpl();
			return rptManager;
		}
	}
}
