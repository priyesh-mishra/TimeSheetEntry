package com.trg.tsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trg.tsu.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
