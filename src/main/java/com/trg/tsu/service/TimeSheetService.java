package com.trg.tsu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.trg.tsu.model.TimeSheet;;

public interface TimeSheetService {
    void save(TimeSheet timeSheet);
   
    void save(List<TimeSheet> fileList);
    
    public Map findByFileName(String fileName);
    
    public Map searchRecords(Date fromDate, Date toDate);
}


