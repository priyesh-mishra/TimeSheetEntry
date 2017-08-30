package com.trg.tsu.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.querydsl.jpa.impl.JPAQuery;
import com.trg.tsu.model.TimeSheet;
import com.trg.tsu.model.QTimeSheet;

import org.springframework.stereotype.Component;

@Component
public class TimeSheetDaoImpl implements TimeSheetDao {

	 @PersistenceContext
	 private EntityManager em;
	
	  @Override
	    public List findAllFiles() {
		  final JPAQuery<TimeSheet> query = new JPAQuery<>(em);
	        final QTimeSheet timeSheet = QTimeSheet.timeSheet;

	        List<TimeSheet> timeSheetList= query.from(timeSheet).fetch();
	        List<String> fileList = new ArrayList<String>();
	        
	        for(TimeSheet ts : timeSheetList){
	          fileList.add(ts.getFileName());
	        }
	        
	        List<String> uniqueNameList = new ArrayList<>(new HashSet<>(fileList));

	        return uniqueNameList;

	    }


}
