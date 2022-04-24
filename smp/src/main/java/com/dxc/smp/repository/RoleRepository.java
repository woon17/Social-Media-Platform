package com.dxc.smp.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dxc.smp.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, String>{

}
