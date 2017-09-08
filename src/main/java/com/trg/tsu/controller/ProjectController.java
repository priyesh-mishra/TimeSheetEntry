package com.trg.tsu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trg.tsu.model.Project;
import com.trg.tsu.model.User;
import com.trg.tsu.service.ProjectService;
import com.trg.tsu.service.UserService;
import com.trg.tsu.validator.ProjectValidator;

@Controller
public class ProjectController {
	 @Autowired
	 private ProjectValidator projectValidator;
	 @Autowired
	 private ProjectService projectService;
	   @Autowired
	    private UserService userService;

	   
	   
	   @RequestMapping(value = {"/abc"}, method = RequestMethod.GET)
	    public ModelAndView abc(Model model) {
	    	  ModelAndView mav = new ModelAndView("abc");
	          Project usuario = new Project();
	          
	          List<User> userList = userService.findAll();
	          mav.getModelMap().put("abc", usuario);
		        model.addAttribute("projectForm", new Project());
		        model.addAttribute("userList", userList);
	          return mav;
	    }    
	    
	    @RequestMapping(value = "/abc", method = RequestMethod.POST)
	    public String addProject(@ModelAttribute("projectForm") Project projectForm, BindingResult bindingResult, Model model) {
		  projectValidator.validate(projectForm, bindingResult);

	        if (bindingResult.hasErrors()) {
	            return "abc";
	        }

	        projectService.save(projectForm);
	        return "redirect:/welcome";
	    }
}
