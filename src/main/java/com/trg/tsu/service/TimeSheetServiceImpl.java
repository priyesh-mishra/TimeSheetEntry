package com.trg.tsu.service;

import com.trg.tsu.model.TimeSheet;
import com.trg.tsu.repository.TimeSheetRepository;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeSheetServiceImpl implements TimeSheetService {
    @Autowired
    private TimeSheetRepository TimeSheet;
    @Override
    public void save(TimeSheet file) {
    	file.setDate(file.getDate());
    	file.setEmpId(file.getEmpId());
    	file.setLoggedHours(file.getLoggedHours());
    	file.setProjectCode(file.getProjectCode());
    	file.setRemarks(file.getRemarks());
    	file.setTaskDescription(file.getTaskDescription());
    	TimeSheet.save(file);
    }
    
    
    @Override
    public void save(List<TimeSheet> fileList) {
    	for (int i = 0; i < fileList.size(); i++)
    	{
    	    TimeSheet sheet = fileList.get(i);
    	    save(sheet);
    	}
    }
}