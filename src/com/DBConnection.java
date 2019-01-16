package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	
	//private static String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=BaiduFace;integratedSecurity=true;characterEncoding=utf-8;";
	//private static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url = "jdbc:mysql://localhost:3306/BaiduFace?user=root&password=&useUnicode=true&characterEncoding=UTF8";
	private static String driverName = "com.mysql.jdbc.Driver";
    
	static {
		try {
		     Class.forName(driverName);  //����JDBC��������
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		try {
			Connection con = DriverManager.getConnection(url); //�������ݿ�����
			return con;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	} 
	
	//�ر�JDBC����
	public static void free(Connection con , PreparedStatement ps , ResultSet rs) {
		try {
			if(con != null) {
				con.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(rs != null) {
						rs.close();
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
