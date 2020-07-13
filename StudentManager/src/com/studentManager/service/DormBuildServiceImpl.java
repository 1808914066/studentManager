package com.studentManager.service;

import java.util.List;

import com.studentManager.bean.DormBuild;
import com.studentManager.dao.DormBuildDao;
import com.studentManager.dao.DormBuildDaoImpl;

public class DormBuildServiceImpl implements DormBuildService {
private DormBuildDao dormBuildDao = new DormBuildDaoImpl();
	@Override
	public DormBuild findByName(String name) {
		return dormBuildDao.findByName(name);
	}
	@Override
	public void save(DormBuild build) {
		// TODO Auto-generated method stub
		dormBuildDao.save(build);
	}
	@Override
	public List<DormBuild> find() {
		// TODO Auto-generated method stub
		return dormBuildDao.find();
	}
	@Override
	public DormBuild findById(Integer id) {
		// TODO Auto-generated method stub
		return dormBuildDao.findById(id);
	}
	@Override
	public void update(DormBuild dormBuild) {
		// TODO Auto-generated method stub
		dormBuildDao.update(dormBuild);
		
	}
	@Override
	public List<DormBuild> findByUserId(Integer id) {
		// TODO Auto-generated method stub
		return dormBuildDao.findByUserId(id);
	}
	@Override
	public void deleteByUserId(Integer id) {
		// TODO Auto-generated method stub
		dormBuildDao.deleteByUserId(id);
	}
	@Override
	public void saveManagerAndBuild(Integer id, String[] dormBuildIds) {
		// TODO Auto-generated method stub
		dormBuildDao.saveManagerAndBuild(id, dormBuildIds);
	}
	@Override
	public List<DormBuild> findAll() {
		// TODO Auto-generated method stub
		return dormBuildDao.findAll();
	}

}
