package com.studentManager.service;

import java.util.ArrayList;
import java.util.List;

import com.studentManager.bean.DormBuild;
import com.studentManager.bean.User;
import com.studentManager.dao.DormBuildDao;
import com.studentManager.dao.DormBuildDaoImpl;
import com.studentManager.dao.UserDao;
import com.studentManager.dao.UserdaoImpl;
import com.sun.org.apache.bcel.internal.generic.IFNULL;


public class UserServiceImpl implements UserService {
	private UserDao UserDao = new UserdaoImpl();
	private DormBuildDao dormBuildDao = new DormBuildDaoImpl();
	@Override
	public User findByStuCodeAndPass(String name, String password) {
		
		return UserDao.findByStuCodeAndPass(name,password);
	}
	
	@Override
	public void saveManager(User user,String[] dormBuildIds) {
		String managerStuCodeMax = UserDao.findManagerStuCodeMax();
		System.out.println("managerStuCodeMax："+managerStuCodeMax);
		user.setStuCode(managerStuCodeMax);
		//保存宿舍管理员
		Integer userId = UserDao.saveManager(user);
		System.out.println("userId:"+userId);
		if (userId != null) {
			//保存宿舍管理员和宿舍楼中间表
			dormBuildDao.saveManagerAndBuild(userId,dormBuildIds);
		}else {
			System.out.println("id为空");
		}
	}

	@Override
	public List<User> findManager(String searchType, String keyword) {
		StringBuffer sql = new StringBuffer("select * from tb_user where role_id=1");
		if (keyword != null&&!keyword.equals("")) {
			if ("name".equals(searchType)) {
				sql.append(" and name like '%"+keyword+"%'");
			}else if ("sex".equals(searchType)) {
				sql.append(" and sex ='"+keyword.trim()+"'");
			}else if ("tel".equals(searchType)) {
				sql.append(" and tel ='"+keyword.trim()+"'");
			}
		}
		System.out.println("sql:"+sql.toString());
		
		List<User> users=UserDao.findManager(sql.toString());
		System.out.println("users:"+users);
		for(User user:users) {
			List<DormBuild> builds = dormBuildDao.findByUserId(user.getId());
			user.setDormBuilds(builds);
		}
		return users;
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return UserDao.findById(id);
	}

	@Override
	public void updateManager(User user) {
		// TODO Auto-generated method stub
		UserDao.updateManager(user);
	}

	@Override
	public User findByStuCode(String stuCode) {
		// TODO Auto-generated method stub
		return UserDao.findByStuCode(stuCode);
	}

	@Override
	public void saveStudent(User user) {
		// TODO Auto-generated method stub
		UserDao.saveStudent(user);
	}

	@Override
	public List<User> findStudent(String dormBuildId, String searchType, String keyword, User user) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		//不管当前用户角色是谁，都要查询学生，所以role—id = 2

		sql.append("select user.*,build.name buildName,build.* from tb_user user left join tb_dormbuild build on build.`id`=user.dormBuildId where user.role_id=2");
		if (keyword != null && !keyword.equals("") && "name".equals(searchType)) {
			sql.append(" and user.name like '%"+keyword.trim()+"%'");
		}else if (keyword != null && !keyword.equals("") && "stuCode".equals(searchType)) {
			sql.append(" and user.stu_code = '"+keyword.trim()+"'");
		}else if (keyword != null && !keyword.equals("") && "dormCode".equals(searchType)) {
			sql.append(" and user.dorm_Code = '"+keyword.trim()+"'");
		}else if (keyword != null && !keyword.equals("") && "sex".equals(searchType)) {
			sql.append(" and user.sex = '"+keyword.trim()+"'");
		}else if (keyword != null && !keyword.equals("") && "tel".equals(searchType)) {
			sql.append(" and user.tel = '"+keyword.trim()+"'");
		}
		
		if (dormBuildId != null  && !dormBuildId.equals("")) {
			sql.append(" and user.dormBuildId = '"+dormBuildId+"'");
		}
		
		//判断当前用户是否为宿管，如果是则需要查询其管理的所有宿舍楼id
		if(user.getRoleId().equals(1)) {
		List<DormBuild> builds = dormBuildDao.findByUserId(user.getId());
		sql.append(" and user.dormBuildId in (");
		for (int i = 0; i < builds.size(); i++) {
			//sql语句的拼接
			sql.append(builds.get(i).getId()+",");
		}
		//删除最后一个逗号
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		}
		System.out.println("sql:"+sql);
		List<User> students = UserDao.findStudent(sql.toString());
		
		System.out.println("students======"+students);
		return students;
	}

	@Override
	public void updateStudent(User studentUpdate) {
		// TODO Auto-generated method stub
		UserDao.updateStudent(studentUpdate);
		
	}

	@Override
	public User findByUserAndManager(Integer id, User user) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from tb_user user where user.id="+id);
		if (user.getRoleId() != null && user.getRoleId().equals(1)) {
			//表示当前登录用户角色是用户管理员，要求修改的学生必须居住在宿舍管理员管理的宿舍楼中
			List<DormBuild> builds = dormBuildDao.findByUserId(user.getId());
			
				sql.append(" and user.dormBuildId in (");
				for (int i = 0; i < builds.size(); i++) {
					//sql语句的拼接
					sql.append(builds.get(i).getId()+",");
				}
				//删除最后一个逗号
				sql.deleteCharAt(sql.length()-1);
				sql.append(")");
				}
			
		return UserDao.findByUserAndManager(sql.toString());
	}

	@Override
	public User findByStuCodeAndManager(String stuCode, User userCurr) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from tb_user user where user.role_id =2 and user.stu_code="+stuCode);
		Integer roleId = userCurr.getRoleId();
		if (roleId != null && roleId.equals(1)) {
			//表示当前登录用户角色是用户管理员，要求修改的学生必须居住在宿舍管理员管理的宿舍楼中
			List<DormBuild> builds = dormBuildDao.findByUserId(userCurr.getId());
			
				sql.append(" and user.dormBuildId in (");
				for (int i = 0; i < builds.size(); i++) {
					//sql语句的拼接
					sql.append(builds.get(i).getId()+",");
				}
				//删除最后一个逗号
				sql.deleteCharAt(sql.length()-1);
				sql.append(")");
				
				return UserDao.findByStuCodeAndManager(sql.toString());
			}
			if(roleId !=null && roleId.equals(2)) {
				return null;
			}
			return UserDao.findByStuCodeAndManager(sql.toString());
	}

	@Override
	public void updatePassWord(User userCurr) {
		// TODO Auto-generated method stub
		UserDao.updatePassWord(userCurr);
	}

	
}

