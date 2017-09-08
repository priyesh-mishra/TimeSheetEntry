package com.trg.tsu.controller;

import com.trg.tsu.dao.TimeSheetDao;
import com.trg.tsu.model.Project;
import com.trg.tsu.model.User;
import com.trg.tsu.service.ProjectService;
import com.trg.tsu.service.SecurityService;
import com.trg.tsu.service.UserService;
import com.trg.tsu.validator.ProjectValidator;
import com.trg.tsu.validator.UserValidator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EntityManager em;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private TimeSheetDao timeSheet;
    @Autowired
    private UserValidator userValidator;
    @Autowired
	 private ProjectValidator projectValidator;
	 @Autowired
	 private ProjectService projectService;
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }    

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
    	
    	List fileList = timeSheet.findAllFiles();
    	   model.addAttribute("fileList", fileList);
        return "welcome";
    }
    
    
   
}
