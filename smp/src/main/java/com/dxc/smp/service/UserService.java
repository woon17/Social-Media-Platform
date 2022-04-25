package com.dxc.smp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
    private PasswordEncoder passwordEncoder;

//	public void initRoleAndUser() {
//
//		Role adminRole = new Role();
//		adminRole.setRoleName("Admin");
//		adminRole.setRoleDescription("Admin role");
//		roleRepository.save(adminRole);
//
//		Role userRole = new Role();
//		userRole.setRoleName("User");
//		userRole.setRoleDescription("Default role for newly created record");
//		roleRepository.save(userRole);
//
//		User adminUser = new User();
//		adminUser.setUserName("admin123");
//		adminUser.setUserPassword(("admin@pass"));
//		adminUser.setUserFirstName("admin");
//		adminUser.setUserLastName("admin");
//		Set<Role> adminRoles = new HashSet<>();
//		adminRoles.add(adminRole);
//		adminUser.setRole(adminRoles);
//		userRepository.save(adminUser);
//
//		User user1 = new User();
//		user1.setUserName("raj123");
//		user1.setUserPassword("raj@123");
//		user1.setUserFirstName("raj");
//		user1.setUserLastName("sharma");
//		Set<Role> userRoles = new HashSet<>();
//		userRoles.add(userRole);
//		user1.setRole(userRoles);
//		userRepository.save(user1);
//
//		User user2 = new User();
//		user2.setUserName("shufa");
//		user2.setUserPassword("shufa@123");
//		user2.setUserFirstName("shufa");
//		user2.setUserLastName("wen");
//		Set<Role> userRoles2 = new HashSet<>();
//		userRoles2.add(userRole);
//		user2.setRole(userRoles2);
//		userRepository.save(user2);
//	}

	public User registerNewUser(User user) {
		Role role = roleRepository.findById("User").get();
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(role);
		user.setRole(userRoles);
		user.setUserPassword(getEncodedPassword(user.getUserPassword()));

		return userRepository.save(user);
	}
	
	public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}