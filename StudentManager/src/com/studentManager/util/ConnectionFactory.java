package com.studentManager.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
//���ӹ���
public class ConnectionFactory {
	//��ȡ����Դ ��ȡc3p0-config.xml�ļ�
	private static DataSource dataSource = new ComboPooledDataSource();
	//��ȡ����
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
		//�ͷ���Դ
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
