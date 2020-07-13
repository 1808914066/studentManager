package com.studentManager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.studentManager.bean.DormBuild;
import com.studentManager.bean.User;
import com.studentManager.service.UserService;
import com.studentManager.util.ConnectionFactory;
import com.sun.crypto.provider.RSACipher;
import com.sun.xml.internal.bind.v2.model.core.ID;

public class UserdaoImpl implements UserDao {

	@Override
	public User findByStuCodeAndPass(String stuCode, String password) {
		// TODO Auto-generated method stub
		
		//1.获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//2.准备sql语句
			String sql = "select * from tb_user where stu_code= ? and password = ? and disabled=0";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);		
			preparedStatement.setString(1, stuCode);
			preparedStatement.setString(2, password);
			
			rs =  preparedStatement.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				//获取数据库，返回对象
				user.setId(rs.getInt("id"));
				user.setCreatUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				return user;
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
public String findManagerStuCodeMax() {
		//1.获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//2.准备sql语句
			String sql = "select IFNULL(Max(stu_code),171006)+1 from tb_user where role_id=1";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);								
			rs =  preparedStatement.executeQuery();
			
			rs.next();
			return rs.getString(1);
			
			
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
	public Integer saveManager(User user) {
		// TODO Auto-generated method stub
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//2.准备sql语句
			String sql = "insert into tb_user(name,passWord,stu_code,sex,tel,role_id,create_user_id,disabled) value(?,?,?,?,?,?,?,?)";
			//3.执行sql RETURN_GENERATED_KEYS指定返回生成的主键			
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);								
						
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassWord());
			preparedStatement.setString(3, user.getStuCode());
			preparedStatement.setString(4, user.getSex());
			preparedStatement.setString(5, user.getTel());
			preparedStatement.setInt(6, user.getRoleId());
			preparedStatement.setInt(7, user.getCreatUserId());
			preparedStatement.setInt(8, user.getDisabled());
			
			preparedStatement.executeUpdate();
			
			resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			return resultSet.getInt(1);
			//获取执行结果
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public List<User> findManager(String sql) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//3.执行sql RETURN_GENERATED_KEYS指定返回生成的主键			
			preparedStatement = connection.prepareStatement(sql);								
			rs=preparedStatement.executeQuery();
			List<User> users = new ArrayList<User>();
			while(rs.next()) {
				User user = new User();
				//获取数据库，返回对象
				user.setId(rs.getInt("id"));
				user.setCreatUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				users.add(user);
			}					
			return users;
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
	public User findById(int id) {
		//1.获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//2.准备sql语句
			String sql = "select * from tb_user where id= ? ";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);		
			preparedStatement.setInt(1, id);	
			rs =  preparedStatement.executeQuery();
			while(rs.next()) {
				User user = new User();
				//获取数据库，返回对象
				user.setId(rs.getInt("id"));
				user.setCreatUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				return user;
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
	public void updateManager(User user) {
		// TODO Auto-generated method stub
	Connection connection = ConnectionFactory.getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	try {
		//2.准备sql语句
		String sql = "update tb_user set name=?,passWord=?,sex=?,tel=?,disabled=? where id = ?";
		//3.执行sql RETURN_GENERATED_KEYS指定返回生成的主键			
		preparedStatement = connection.prepareStatement(sql);								
					
		preparedStatement.setString(1, user.getName());
		preparedStatement.setString(2, user.getPassWord());
		preparedStatement.setString(3, user.getSex());
		preparedStatement.setString(4, user.getTel());
		preparedStatement.setInt(5, user.getDisabled());
		preparedStatement.setInt(6, user.getId());
		preparedStatement.executeUpdate();
		
		preparedStatement.executeUpdate();
		//获取执行结果
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		ConnectionFactory.close(connection, preparedStatement, resultSet);
	}
}

	@Override
	public User findByStuCode(String stuCode) {
		// TODO Auto-generated method stub
		//1.获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//2.准备sql语句
			String sql = "select * from tb_user where stu_code= ?";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);		
			preparedStatement.setString(1, stuCode);
			
			rs =  preparedStatement.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				//获取数据库，返回对象
				user.setId(rs.getInt("id"));
				user.setCreatUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
			
				
				return user;
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
	public void saveStudent(User user) {
		// TODO Auto-generated method stub
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//2.准备sql语句
			String sql = "insert into tb_user(name,passWord,stu_code,dorm_Code,sex,tel,dormBuildId,role_id,create_user_id) value(?,?,?,?,?,?,?,?,?)";
			//3.执行sql RETURN_GENERATED_KEYS指定返回生成的主键			
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);								
						
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassWord());
			preparedStatement.setString(3, user.getStuCode());
			preparedStatement.setString(4, user.getDormCode());
			preparedStatement.setString(5, user.getSex());
			preparedStatement.setString(6, user.getTel());
			preparedStatement.setInt(7, user.getDormBuildId());
			preparedStatement.setInt(8, user.getRoleId());
			preparedStatement.setInt(9, user.getCreatUserId());
			
			
			preparedStatement.executeUpdate();
			
			/*
			 * resultSet = preparedStatement.getGeneratedKeys(); 
			 * resultSet.next(); 
			 * Integer id = resultSet.getInt(1);
			 */
			//获取执行结果
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public List<User> findStudent(String sql) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//3.执行sql RETURN_GENERATED_KEYS指定返回生成的主键			
			preparedStatement = connection.prepareStatement(sql);								
			rs=preparedStatement.executeQuery();
			List<User> users = new ArrayList<User>();
			while(rs.next()) {
				User user = new User();
				//获取数据库，返回对象
				user.setId(rs.getInt("id"));
				user.setCreatUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				user.setDormCode(rs.getString("dorm_Code"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				DormBuild build = new DormBuild();
				build.setId(rs.getInt("dormBuildId"));
				build.setName(rs.getString("buildName"));
				build.setRemark(rs.getString("remark"));
				user.setDormBuild(build);
				
				users.add(user);
			}					
			return users;
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
	public void updateStudent(User user) {
		// TODO Auto-generated method stub
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//2.准备sql语句
			String sql = "update tb_user set name=?,passWord=?,sex=?,tel=?,disabled=?,stu_code=?,dorm_Code=?,dormBuildId=? where id = ?";
			//3.执行sql RETURN_GENERATED_KEYS指定返回生成的主键			
			preparedStatement = connection.prepareStatement(sql);								
						
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassWord());
			preparedStatement.setString(3, user.getSex());
			preparedStatement.setString(4, user.getTel());
			preparedStatement.setInt(5, user.getDisabled());
			preparedStatement.setString(6, user.getStuCode());
			preparedStatement.setString(7, user.getDormCode());
			preparedStatement.setInt(8, user.getDormBuildId());
			preparedStatement.setInt(9, user.getId());
			preparedStatement.executeUpdate();				
			//获取执行结果
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public User findByUserAndManager(String sql) {
		// TODO Auto-generated method stub
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);		
			
			rs =  preparedStatement.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				//获取数据库，返回对象
				user.setId(rs.getInt("id"));
				user.setCreatUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				return user;
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
	public User findByStuCodeAndManager(String sql) {
		// TODO Auto-generated method stub
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);		
			
			rs =  preparedStatement.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				//获取数据库，返回对象
				user.setId(rs.getInt("id"));
				user.setCreatUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				return user;
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
	public void updatePassWord(User userCurr) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//2.准备sql语句
			String sql = "update tb_user set passWord=? where id = ?";
			//3.执行sql RETURN_GENERATED_KEYS指定返回生成的主键			
			preparedStatement = connection.prepareStatement(sql);								
						
			preparedStatement.setString(1, userCurr.getPassWord());
			preparedStatement.setInt(2, userCurr.getId());
			preparedStatement.executeUpdate();				
			//获取执行结果
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}		
	}
}
