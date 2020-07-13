package com.studentManager.dao;

import java.util.List;

import com.studentManager.bean.Record;

public interface RecordDao {

	void save(Record record);

	List<Record> find(String sql);

	Record findById(int id);

	void update(Record record);

}
