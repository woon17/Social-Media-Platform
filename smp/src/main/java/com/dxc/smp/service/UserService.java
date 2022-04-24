package com.dxc.smp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.smp.entity.Role;
import com.dxc.smp.entity.User;
import com.dxc.smp.repository.RoleRepository;
import com.dxc.smp.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public User registerNewUser(User user) {
		Role role = roleRepository.findById("User").get();
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(role);
		user.setRole(userRoles);
		user.setUserPassword(user.getUserPassword());

		return userRepository.save(user);
	}
}