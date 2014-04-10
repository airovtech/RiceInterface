/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 9.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.test;

import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Tester {

	public static void main(String[] args) {

		try {
			String resource = "net/smartworks/conf/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			SqlSession sqlSession= sqlSessionFactory.openSession();
			//Connection conn = sqlSession.getConnection();
			
			List<FileVo> list = sqlSession.selectList("getFile");
			
			for (FileVo vo : list) {
				System.out.println(vo.getFileSize());
			}
			List<FileVo> list2 = sqlSession.selectList("getFileName");
			
			for (FileVo vo : list2) {
				System.out.println(vo.getFileSize());
			}
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
