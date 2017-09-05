package com.trg.tsu.service;

import java.util.List;

import com.trg.tsu.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
    
    List<User> findAll();
}
