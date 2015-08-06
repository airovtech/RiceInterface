/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.factory;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SessionFactory1 {

	private static SessionFactory1 sessionFactory;
	public static SessionFactory1 getInstance() {
		if (sessionFactory != null) {
			return sessionFactory;
		} else {
			sessionFactory = new SessionFactory1();
			return sessionFactory;
		}
	}
	public SqlSessionFactory getSqlSessionFactory() throws Exception {
		return getSqlSessionFactory(null);
	}
	public SqlSessionFactory getSqlSessionFactory(String environment) throws Exception {
//		String resource = new String(("net/smartworks/conf/mybatis-config-mdb.xml").getBytes("euc-kr"), "ISO-8859-1");
		String resource = "net/smartworks/conf/mybatis-config-mdb.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		
		SqlSessionFactory sqlSessionFactory = null;
		if (environment != null) {
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, environment);
		} else {
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		}
		return sqlSessionFactory;
	}
}
