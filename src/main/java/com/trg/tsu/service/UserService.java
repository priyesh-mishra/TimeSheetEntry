package com.trg.tsu.service;

import com.trg.tsu.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
