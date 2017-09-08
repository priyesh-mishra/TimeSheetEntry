package com.trg.tsu.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trg.tsu.model.MyCell;
import com.trg.tsu.model.Project;
import com.trg.tsu.model.TimeSheet;
import com.trg.tsu.model.User;
import com.trg.tsu.service.TimeSheetService;
import com.trg.tsu.service.UserService;

@Controller
public class TimesheetController {

	@Autowired
	private TimeSheetService timsheetService;
	@Autowired
	private UserService userService;
	@Autowired
	private FileuploadController fileuploadController;
	
	private org.springframework.security.core.userdetails.User loggedUser;
	
	
	
	 @RequestMapping(value = {"/timesheet"}, method = RequestMethod.GET)
	    public ModelAndView abc(Model model) {
	    	  ModelAndView mav = new ModelAndView("timesheet");
	          return mav;
	    }  
	 
	 @RequestMapping(method = RequestMethod.GET, value = {"/timesheetEntry"})
	 public String insertTaskSheet(Model model,String filePath
			 ,@RequestParam("empId") Long[] empId
			 ,@RequestParam("date") Date[] date
			 ,@RequestParam("projectCode") String[] projectCode
			 ,@RequestParam("taskDescription") String[] taskDescription
			 ,@RequestParam("loggedHours") Double[] loggedHours
			 ,@RequestParam("remarks") String[] remarks) throws IOException {
		 loggedUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 	 User user = userService.findByUsername(loggedUser.getUsername());	   
	 	String fileName = "Tasksheet_"+new SimpleDateFormat("MM-dd-yyyy").format(new Date())+".xls";
	   for(int i = 0; i<empId.length;i++) {
		   TimeSheet timeSheet = new TimeSheet();
		   timeSheet.setEmpId(empId[i]);
		   timeSheet.setDate(date[i]);
		   timeSheet.setProjectCode(projectCode[i]);
		   timeSheet.setTaskDescription(taskDescription[i]);
		   timeSheet.setLoggedHours(loggedHours[i]);
		   timeSheet.setRemarks(remarks[i]);
		   timeSheet.setFileName(fileName);
		   timeSheet.setUser(user);
		   timsheetService.save(timeSheet);
	   }
	   String result = fileuploadController.writeTimesheet(fileName);
	   System.out.println(result);
	   return "timesheet";
	 }
	 
	 
	 @RequestMapping(value = {"/searchRecord"}, method = RequestMethod.GET)
	    public ModelAndView searchRecord(Model model) {
	    	  ModelAndView mav = new ModelAndView("searchrecord");
	          return mav;
	    }  
	 
	 
	 @RequestMapping(method = RequestMethod.POST, value = {"/searchRecord"})
	 public String searchRecord(Model model,@RequestParam("fromDate") Date fromDate,
			 @RequestParam("toDate") Date toDate) {
		 Map<Integer, List<MyCell>> map =timsheetService.searchRecords(fromDate, toDate);
		 if(map.isEmpty()) {
			 model.addAttribute("norecord", "No record found for provided criteria");
		 }
		 List<MyCell> headers = new ArrayList<MyCell>();
		 headers.add(new MyCell("Employee ID"));
		 headers.add(new MyCell("Date"));
		 headers.add(new MyCell("Project Code"));
		 headers.add(new MyCell("Task Description"));
		 headers.add(new MyCell("Logged Hours"));
		 headers.add(new MyCell("Remarks"));
		 map.put(new Integer(0), headers);
		 model.addAttribute("data", map);
		 return "searchrecord";
	 }
}
