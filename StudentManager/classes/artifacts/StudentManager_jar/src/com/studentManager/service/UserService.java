package com.studentManager.service;

import java.util.List;

import com.studentManager.bean.User;

public interface UserService {

	User findByStuCodeAndPass(String name, String password);

	void saveManager(User user, String[] dormBuildIds);

	List<User> findManager(String searchType, String keyword);

	User findById(int id);

	void updateManager(User user);

	User findByStuCode(String stuCode);

	void saveStudent(User user);

	List<User> findStudent(String dormBuildId, String searchType, String keyword, User user);

	void updateStudent(User studentUpdate);

	User findByUserAndManager(Integer id, User user);

	User findByStuCodeAndManager(String stuCode, User userCurr);

	void updatePassWord(User userCurr);


}
