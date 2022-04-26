package com.dxc.smp.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dxc.smp.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, String>{
	public Optional<Role> findById(String role);
}
