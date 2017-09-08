package com.trg.tsu.dao;

import java.util.Date;
import java.util.List;

import com.trg.tsu.model.TimeSheet;

public interface TimeSheetDao {

	List findAllFiles();
	
	List findByName(String fileName);
	
	List<TimeSheet> findByDate(Date fromDate, Date toDate);
}
