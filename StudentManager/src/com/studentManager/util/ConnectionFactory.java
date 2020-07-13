package com.studentManager.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
//连接工厂
public class ConnectionFactory {
	//获取数据源 读取c3p0-config.xml文件
	private static DataSource dataSource = new ComboPooledDataSource();
	//获取连接
	public static Connection getConnection() {
		// TODO Auto-generated method stub
		try {
			return dataSource.getConnection();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		return null;
	}
	public static void close(Connection connection ,PreparedStatement preparedStatement,ResultSet resultSet) {
		//释放资源
		try {
			if(resultSet!=null) resultSet.close();
			if(preparedStatement!=null) preparedStatement.close();
			if(connection!=null) connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
