package com.studentManager.service;

import java.util.List;

import com.studentManager.bean.DormBuild;
import com.studentManager.bean.Record;
import com.studentManager.bean.User;
import com.studentManager.dao.DormBuildDao;
import com.studentManager.dao.DormBuildDaoImpl;
import com.studentManager.dao.RecordDao;
import com.studentManager.dao.RecordDaoImpl;

public class RecordServiceImpl implements RecordService {
	private RecordDao recordDao = new RecordDaoImpl();
	private DormBuildDao dormBuildDao = new DormBuildDaoImpl();
	@Override
	public void save(Record record) {
		recordDao.save(record);
		
	}
	@Override
	public List<Record> findRecords(String startDate, String endDate, String dormBuildId, String searchType,
			String keyword, User userCurr) {
		StringBuffer sql = new StringBuffer();	
		sql.append("select record.*,record.id recordId,record.disabled recordDisabled,user.*,build.name buildName  from tb_record record "
				+ " left join tb_user user on user.id = record.student_id "
				+ " left join tb_dormbuild build on build.id=User.`dormBuildId` where 1=1");
		if (keyword != null && !keyword.equals("") && "name".equals(searchType)) {
			sql.append(" and user.name like '%"+keyword.trim()+"%'");
		}else if (keyword != null && !keyword.equals("") && "sex".equals(searchType)) {
			sql.append(" and user.sex = '"+keyword.trim()+"'");
		}else if (keyword != null && !keyword.equals("") && "stuCode".equals(searchType)) {
			sql.append(" and user.stu_code = '"+keyword.trim()+"'");
		}else if (keyword != null && !keyword.equals("") && "dormCode".equals(searchType)) {
			sql.append(" and user.dorm_Code = '"+keyword.trim()+"'");
		}		
		if (dormBuildId != null  && !dormBuildId.equals("")) {
			sql.append(" and user.dormBuildId = "+dormBuildId);
		}
		if (startDate != null  && !startDate.equals("")) {
			sql.append(" and record.date >= '"+startDate+"'");
		}
		if (endDate != null  && !endDate.equals("")) {
			sql.append(" and record.date <= '"+endDate+"'");
		}
		
		//获取当前用户登录角色
		Integer roleId = userCurr.getRoleId();
		if(roleId !=null && roleId.equals(1)) {
		List<DormBuild> builds = dormBuildDao.findByUserId(userCurr.getId());
		sql.append(" and user.dormBuildId in (");
		for (int i = 0; i < builds.size(); i++) {
			//sql语句的拼接
			sql.append(builds.get(i).getId()+",");
		}
		//删除最后一个逗号
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		}
		if(roleId !=null && roleId.equals(2)) {
			//当前登录用户为学生
			sql.append(" and user.ID ="+userCurr.getId());
		}
		//日期递减
		sql.append(" order by record.date desc ");
		System.out.println("sql:"+sql.toString());
		return recordDao.find(sql.toString());
	}
	@Override
	public Record findById(int id) {
		// TODO Auto-generated method stub
		return recordDao.findById(id);
	}
	@Override
	public void update(Record record) {
		// TODO Auto-generated method stub
		recordDao.update(record);
	}

}
