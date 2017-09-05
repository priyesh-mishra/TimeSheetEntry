package com.trg.tsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trg.tsu.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByProjCode(String projCode);
}
