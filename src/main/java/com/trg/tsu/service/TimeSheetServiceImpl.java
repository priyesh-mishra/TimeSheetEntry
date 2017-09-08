package com.trg.tsu.service;

import com.trg.tsu.dao.TimeSheetDao;
import com.trg.tsu.model.MyCell;
import com.trg.tsu.model.TimeSheet;
import com.trg.tsu.repository.TimeSheetRepository;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeSheetServiceImpl implements TimeSheetService {
    @Autowired
    private TimeSheetRepository timeSheetRepository;
    @Autowired
    private TimeSheetDao timeSheetDao;
    @Override
    public void save(TimeSheet file) {
    	file.setDate(file.getDate());
    	file.setEmpId(file.getEmpId());
    	file.setLoggedHours(file.getLoggedHours());
    	file.setProjectCode(file.getProjectCode());
    	file.setRemarks(file.getRemarks());
    	file.setTaskDescription(file.getTaskDescription());
    	file.setSupCode(file.getSupCode());
    	timeSheetRepository.save(file);
    }
    
    
    @Override
    public void save(List<TimeSheet> fileList) {
    	for (int i = 0; i < fileList.size(); i++)
    	{
    	    TimeSheet sheet = fileList.get(i);
    	    save(sheet);
    	}
    }
    
    @Override
    public Map findByFileName(String fileName) {
    	List<TimeSheet> list = new ArrayList<TimeSheet>();
    	Map<String, Object[]> data = new HashMap<String, Object[]>();
    	list= timeSheetDao.findByName(fileName);
    	int index = 0;
    	data.put(String.valueOf(index++), new Object[] {"Employee Id", "Date", "Project Code", "Task Description", "Logged hours", "Remarks"});
    	for(TimeSheet timeSheet : list) {
    		data.put(String.valueOf(index), new Object[] {timeSheet.getEmpId(),
    				timeSheet.getDate(),timeSheet.getProjectCode(),
    				timeSheet.getTaskDescription(),
    				timeSheet.getLoggedHours(),timeSheet.getRemarks()});
    		index++;

    	}
    	return data;
    }
    
    @Override
    public Map searchRecords(Date fromDate, Date toDate) {
    	int index =1;
    	List<TimeSheet> list =  timeSheetDao.findByDate(fromDate,toDate);
    	Map<Integer, List<MyCell>> map = new HashMap<>();
    	for(TimeSheet timesheet : list) {
    		List<MyCell> arrList = new ArrayList<MyCell>();
    		arrList.add(new MyCell(timesheet.getEmpId()+""));
    		arrList.add(new MyCell(timesheet.getDate()+""));
    		arrList.add(new MyCell(timesheet.getProjectCode()));
    		arrList.add(new MyCell(timesheet.getTaskDescription()));
    		arrList.add(new MyCell(timesheet.getLoggedHours()+""));
    		arrList.add(new MyCell(timesheet.getRemarks()));
    		map.put(index++,arrList );
    	}
    	return map;
    	 
    }
}