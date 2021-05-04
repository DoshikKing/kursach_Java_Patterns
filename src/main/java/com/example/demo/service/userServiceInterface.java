package com.example.demo.service;

import com.example.demo.components.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface userServiceInterface {
    void addUser(User user);
    User findUser(String name);
    UserDetails loadUserByUsername(String name);
}
