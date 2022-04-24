package com.dxc.smp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.smp.entity.Role;
import com.dxc.smp.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }
}
