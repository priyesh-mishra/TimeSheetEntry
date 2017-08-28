package com.trg.tsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trg.tsu.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
