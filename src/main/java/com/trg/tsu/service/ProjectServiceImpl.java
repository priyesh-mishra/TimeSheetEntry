package com.trg.tsu.service;

import com.trg.tsu.model.Project;
import com.trg.tsu.model.User;
import com.trg.tsu.repository.RoleRepository;
import com.trg.tsu.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void save(Project project) {
    	project.setLocation(project.getLocation());
    	project.setProjCode(project.getProjCode());
    	project.setProjDesc(project.getProjDesc());
    	projectRepository.save(project);
    }

    @Override
    public Project findByProjectCode(String projCode) {
        return projectRepository.findByProjCode(projCode);
    }
}
