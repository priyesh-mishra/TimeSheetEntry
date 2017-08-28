package com.trg.tsu.web;

import com.trg.tsu.model.User;
import com.trg.tsu.service.SecurityService;
import com.trg.tsu.service.UserService;
import com.trg.tsu.validator.UserValidator;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

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
    private String fileLocation;
    
    
 /*   @RequestMapping(method = RequestMethod.GET, value = "/uploadFileUrl")
    public String readPOI(Model model) throws IOException {
     
      if (fileLocation != null) {
          if (fileLocation.endsWith(".xlsx") || fileLocation.endsWith(".xls")) {
              Map<Integer, List<MyCell>> data
                = excelPOIHelper.readExcel(fileLocation);
              model.addAttribute("data", data);
          } else {
              model.addAttribute("message", "Not a valid excel file!");
          }
      } else {
          model.addAttribute("message", "File missing! Please upload an excel file.");
      }
      return "excel";
    }*/
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }
    
    
}
