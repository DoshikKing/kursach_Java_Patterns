package com.example.demo.repos;

import com.example.demo.components.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}

