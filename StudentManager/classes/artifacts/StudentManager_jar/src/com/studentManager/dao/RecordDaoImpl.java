package com.studentManager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.studentManager.bean.DormBuild;
import com.studentManager.bean.Record;
import com.studentManager.bean.User;
import com.studentManager.util.ConnectionFactory;

public class RecordDaoImpl implements RecordDao {

	@Override
	public void save(Record record) {
		// TODO Auto-generated method stub
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			//2.准备sql语句
			String sql = "insert into tb_record(student_id,date,remark,disabled) values(?,?,?,?)";
			//3.执行sql			
			preparedStatement = connection.prepareStatement(sql);								
						
			preparedStatement.setInt(1, record.getId());
			preparedStatement.setDate(2,new Date(record.getDate().getTime()));
			preparedStatement.setString(3, record.getRemark());
			preparedStatement.setInt(4, record.getDisabled());
			
			preparedStatement.executeUpdate();
			

			//获取执行结果
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}

	@Override
	public List<Record> find(String sql) {
		// TODO Auto-generated method stub
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {		
			//3.执行sql			
			preparedStatement = connection.prepareStatement(sql);	
			
			rs = preparedStatement.executeQuery();
			List<Record> records = new ArrayList<Record>();
			
			while (rs.next()) {
				Record record = new Record();
				record.setId(rs.getInt("recordId"));
				record.setDate(rs.getTimestamp("date"));
				record.setRemark(rs.getString("remark"));
				record.setDisabled(rs.getInt("recordDisabled"));
				
				User user = new User();
				user.setId(rs.getInt("student_id"));
				user.setCreatUserId(rs.getInt("create_user_id"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				DormBuild dormBuild = new DormBuild();
				dormBuild.setName(rs.getString("buildName"));
				
				user.setDormBuild(dormBuild);
				record.setUser(user);
				
				records.add(record);
			}
			return records;
						
			//获取执行结果
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public Record findById(int id) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//2.准备sql语句
			StringBuffer sql = new StringBuffer();
			sql.append("select record.*,record.id recordId,record.disabled recordDisabled,user.*  from tb_record record "
					+ " left join tb_user user on user.id = record.student_id "
					+ " where record.id=?");		
			//3.执行sql			
			preparedStatement = connection.prepareStatement(sql.toString());	
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				Record record = new Record();
				record.setId(rs.getInt("recordId"));
				record.setStudentId(rs.getInt("student_id"));
				record.setDate(rs.getTimestamp("date"));
				record.setRemark(rs.getString("remark"));
				record.setDisabled(rs.getInt("recordDisabled"));
				
				User user = new User();
				user.setId(rs.getInt("student_id"));
				user.setCreatUserId(rs.getInt("create_user_id"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				record.setUser(user);
				return record;
			}
						
			//获取执行结果
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public void update(Record record) {
		// TODO Auto-generated method stub
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			//2.准备sql语句
			String sql = "update tb_record set student_id=?, date=?,remark=?,disabled=? where id = ?";
			//3.执行sql			
			preparedStatement = connection.prepareStatement(sql);								
						
			preparedStatement.setInt(1, record.getId());
			preparedStatement.setDate(2,new Date(record.getDate().getTime()));
			preparedStatement.setString(3, record.getRemark());
			preparedStatement.setInt(4, record.getDisabled());
			preparedStatement.setInt(5, record.getId());
			
			preparedStatement.executeUpdate();
			

			//获取执行结果
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}
}
