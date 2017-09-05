package com.trg.tsu.validator;

import com.trg.tsu.model.Project;
import com.trg.tsu.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProjectValidator implements Validator {
    @Autowired
    private ProjectService projectService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Project.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	Project project = (Project) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projCode", "NotEmpty");
        if (project.getProjCode().length() < 3 || project.getProjCode().length() > 32) {
            errors.rejectValue("projCode", "Size.projecCode");
        }
        if (projectService.findByProjectCode(project.getProjCode()) != null) {
            errors.rejectValue("projCode", "Duplicate.project.code");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "NotEmpty");
      
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projOwner", "NotEmpty");
   
    }
}
