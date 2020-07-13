package com.studentManager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.studentManager.bean.DormBuild;
import com.studentManager.bean.User;
import com.studentManager.util.ConnectionFactory;

public class DormBuildDaoImpl implements DormBuildDao {

	@Override
	public DormBuild findByName(String name) {
		//1.获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//2.准备sql语句
			String sql = "select * from tb_dormbuild where name = ?";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);		
			preparedStatement.setString(1, name);
			
			rs =  preparedStatement.executeQuery();
			
			while(rs.next()) {
				DormBuild build = new DormBuild();
				build.setId(rs.getInt("id"));
				build.setName(rs.getString("name"));
				build.setRemark(rs.getString("remark"));
				build.setDisabled(rs.getInt("disabled"));
				
				return build;
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
	public void save(DormBuild build) {
		// TODO Auto-generated method stub
		//1.获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			//2.准备sql语句
			String sql = "insert into tb_dormbuild(name,remark,disabled) values(?,?,?) ";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);		
			preparedStatement.setString(1, build.getName());
			preparedStatement.setString(2, build.getRemark());
			preparedStatement.setInt(3, build.getDisabled());
			
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
	public List<DormBuild> find() {
		// TODO Auto-generated method stub
		//1.获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs =null;
		try {
			//2.准备sql语句
			String sql = "select * from tb_dormbuild ";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);					
			rs =  preparedStatement.executeQuery();
			List<DormBuild> builds = new ArrayList<DormBuild>();
			while(rs.next()) {
				DormBuild build = new DormBuild();
				build.setId(rs.getInt("id"));
				build.setName(rs.getString("name"));
				build.setRemark(rs.getString("remark"));
				build.setDisabled(rs.getInt("disabled"));
				
				builds.add(build);
			}
				return builds;		
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
	public DormBuild findById(Integer id) {
		// TODO Auto-generated method stub
		//1.获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//2.准备sql语句
			String sql = "select * from tb_dormbuild where id = ?";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);					
			preparedStatement.setInt(1, id);
			rs =  preparedStatement.executeQuery();
			
			while(rs.next()) {
				DormBuild build = new DormBuild();
				build.setId(rs.getInt("id"));
				build.setName(rs.getString("name"));
				build.setRemark(rs.getString("remark"));
				build.setDisabled(rs.getInt("disabled"));
				
				return build;
			}
						
		//获取执行结果
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement,rs);
		}
		return null;
	}

	@Override
	public void update(DormBuild build) {
		// TODO Auto-generated method stub
		//1.获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			//2.准备sql语句
			String sql = "update tb_dormbuild SET name=?,remark=?,disabled=? where id = ?";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);		
			preparedStatement.setString(1, build.getName());
			preparedStatement.setString(2, build.getRemark());
			preparedStatement.setInt(3, build.getDisabled());
			preparedStatement.setInt(4, build.getId());
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
	public void saveManagerAndBuild(Integer userId, String[] dormBuildIds) {
		// TODO Auto-generated method stub
		//1.获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			//2.准备sql语句
			String sql = "insert into tb_manage_dormbuild(USER_ID,DormBuild_id)value(?,?)";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);		
			//批处理
			for(String dormBuild:dormBuildIds) {								
				preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, Integer.parseInt(dormBuild));
				preparedStatement.addBatch();
			}
		
			preparedStatement.executeBatch();
									
			//获取执行结果
		} catch (SQLException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				ConnectionFactory.close(connection, preparedStatement, null);
			}
	}

	@Override
	public List<DormBuild> findByUserId(Integer id) {
		// TODO Auto-generated method stub
		//1.获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//2.准备sql语句
			String sql = "select tb_dormbuild.* from tb_manage_dormbuild" + 
					" left join tb_dormbuild on tb_dormbuild.`id` =tb_manage_dormbuild.`dormbuild_id`" + 
					" where user_id = ?";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);		
			preparedStatement.setInt(1, id);			
			rs =  preparedStatement.executeQuery();
			List<DormBuild> builds = new ArrayList<DormBuild>();
			while(rs.next()) {
				DormBuild build = new DormBuild();
				build.setId(rs.getInt("id"));
				build.setName(rs.getString("name"));
				build.setRemark(rs.getString("remark"));
				build.setDisabled(rs.getInt("disabled"));
				
				builds.add(build);
				
			}
			return builds;
						
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
	public void deleteByUserId(Integer id) {
		//1.获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			//2.准备sql语句
			String sql = "delete from tb_manage_dormbuild where user_id = ?";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);		
			preparedStatement.setInt(1, id);
			
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
	public List<DormBuild> findAll() {
		// TODO Auto-generated method stub
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//2.准备sql语句
			String sql = "select * from tb_dormbuild";
			//3.执行sql
			preparedStatement = connection.prepareStatement(sql);				
			rs =  preparedStatement.executeQuery();
			List<DormBuild> builds = new ArrayList<DormBuild>();
			while(rs.next()) {
				DormBuild build = new DormBuild();
				build.setId(rs.getInt("id"));
				build.setName(rs.getString("name"));
				build.setRemark(rs.getString("remark"));
				build.setDisabled(rs.getInt("disabled"));
				
				builds.add(build);
			}
			return builds;			
			//获取执行结果
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

}
