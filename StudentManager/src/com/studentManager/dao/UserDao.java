package com.studentManager.dao;

import java.util.List;

import com.studentManager.bean.User;

public interface UserDao {

	User findByStuCodeAndPass(String name, String password);

	String findManagerStuCodeMax();

	Integer saveManager(User user);

	List<User> findManager(String sql);

	User findById(int id);

	void updateManager(User user);

	User findByStuCode(String stuCode);

	void saveStudent(User user);

	List<User> findStudent(String sql);

	void updateStudent(User studentUpdate);

	User findByUserAndManager(String sql);

	User findByStuCodeAndManager(String sql);

	void updatePassWord(User userCurr);
	

}
