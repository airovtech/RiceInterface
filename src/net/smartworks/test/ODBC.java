package net.smartworks.test;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ODBC {
    public static void main(String[] ar) throws UnsupportedEncodingException {
  try {
   Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
   System.out.println("드라이버 검색 성공!");
  }catch(ClassNotFoundException e) {
   System.err.println("error = " + e);
  }
  
  Connection conn = null;   // DB 연결
  Statement stmt = null;   // 쿼리 처리를 위해 생성
  ResultSet rs = null;   // 쿼리 결과를 임시 저장
  String url = "jdbc:odbc:ADSTLE";
  String id = "MANINSOFT-PC";
  String pass = "";
  
  SimpleDateFormat StringDate = new SimpleDateFormat("yyyy-MM-dd");
  String toDayStr = StringDate.format(new Date());
  System.out.println(toDayStr);
  
 // String query = "select * from DBASE where = '" + toDayStr + "'";
  String query = "select * from DBASE where 날짜 = '2015-07-01'";
  
  System.out.println(query);
  try {
   // 연결, 쿼리 검색, 결과저장
   conn = DriverManager.getConnection(url, id, pass);
   stmt = conn.createStatement();
   rs = stmt.executeQuery(query);
   System.out.println(StringDate);
   // 결과 출력
   while(rs.next()) {      
    String userid = rs.getString(1);  
    String age = rs.getString(2);   
    
    System.out.print(userid + "\t");
    System.out.println(age);    
   }
   
   // 연결 끊기
   rs.close();     
   stmt.close();     
   conn.close();
  }catch(SQLException e) {
   System.err.println("error sql = " + e);
  }
 }
}