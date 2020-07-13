package com.studentManager.service;

import java.util.List;

import com.studentManager.bean.Record;
import com.studentManager.bean.User;

public interface RecordService {

	void save(Record record);

	List<Record> findRecords(String startDate, String endDate, String dormBuildId, String searchType, String keyword,
			User userCurr);

	Record findById(int id);

	void update(Record record);

}
