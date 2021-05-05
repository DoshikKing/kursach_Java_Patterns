package com.example.demo.service;

import com.example.demo.components.Role;

public interface roleServiceInterface {
    Role findById(Long id);
    Role findByRole(String role);
}
