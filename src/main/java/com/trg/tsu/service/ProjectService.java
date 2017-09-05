package com.trg.tsu.service;

import com.trg.tsu.model.Project;
import com.trg.tsu.model.User;

public interface ProjectService {
    void save(Project project);

    Project findByProjectCode(String projectCode);
}
