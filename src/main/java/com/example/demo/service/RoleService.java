package com.example.demo.service;

import com.example.demo.components.Role;
import com.example.demo.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Component
@Transactional
public class RoleService implements roleServiceInterface{

    RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo){
        this.roleRepo = roleRepo;
    }
    @Override
    public Role findById(Long id) {
        return roleRepo.getOne(id);
    }

    @Override
    public Role findByRole(String role) {
        return roleRepo.findByRole(role);
    }
}
