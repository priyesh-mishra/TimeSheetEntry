package com.trg.tsu.service;

import java.util.ArrayList;
import java.util.List;

import com.trg.tsu.model.TimeSheet;;

public interface TimeSheetService {
    void save(TimeSheet timeSheet);
    
    
    void save(List<TimeSheet> fileList);
}


