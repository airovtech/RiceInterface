/* 
 * $Id$
 * created by    : YJ
 * creation-date : 2015. 7. 8.
 * =========================================================
 * Copyright (c) 2015 SmartWorks, Inc. All rights reserved.
 */
package net.smartworks.tle.scheduler.job;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DBConnection {
	private String DB_URL = "Jdbc:Odbc:DBASE";
    private String DB_USER = "Admin";
    private String DB_PASSWORD= "";
    Connection conn = null;
    
    public Connection getConnection() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            Properties props = new Properties();
            props.put("charSet", "8859_1" ); 
            props.put("user", DB_USER);
            props.put("password", DB_PASSWORD);
            conn = DriverManager.getConnection(DB_URL, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

	public void disConnection(ResultSet rs, PreparedStatement psmt,
			Connection conn2) {
		try {
			rs.close();
			psmt.close();
			conn2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
