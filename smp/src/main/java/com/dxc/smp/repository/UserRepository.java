package com.dxc.smp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dxc.smp.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	public void deleteByUserName(String userName);
	public User findByUserName(String userName);
}
