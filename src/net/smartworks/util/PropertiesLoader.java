/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 3. 17.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

	private static Properties prop;
	public static Properties loadProp(String propFileName){
		prop = new Properties();
		try {
			InputStream in = PropertiesLoader.class.getResourceAsStream("/net/smartworks/conf/" + propFileName);
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
}
