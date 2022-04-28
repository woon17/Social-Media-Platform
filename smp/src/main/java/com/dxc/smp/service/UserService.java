package com.dxc.smp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dxc.smp.entity.Role;
import com.dxc.smp.entity.User;
import com.dxc.smp.payload.request.SignUpRequest;
import com.dxc.smp.repository.RoleRepository;
import com.dxc.smp.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// set two default admin with default password when running the application
	public void initRoleAndUser() {

		User admin1 = getUser("admin123");
		if (admin1 == null) {
			admin1 = new User();
			admin1.setUserName("admin321");
			admin1.setUserPassword(getEncodedPassword("admin321"));
			admin1.setUserFirstName("admin");
			admin1.setUserLastName("admin");
			Set<Role> adminRoles = new HashSet<>();
			Role adminRole = new Role();
			adminRole.setRoleName("Admin");
			adminRoles.add(adminRole);
			admin1.setRole(adminRoles);
			userRepository.save(admin1);
		} else {
			admin1.setUserPassword(getEncodedPassword("admin123"));
			userRepository.save(admin1);
		}

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
		User admin2 = getUser("admin321");
		if (admin2 == null) {
			admin2 = new User();
			admin2.setUserName("admin321");
			admin2.setUserPassword(getEncodedPassword("admin321"));
			admin2.setUserFirstName("admin");
			admin2.setUserLastName("admin");
			Set<Role> adminRoles = new HashSet<>();
			Role adminRole = new Role();
			adminRole.setRoleName("Admin");
			adminRoles.add(adminRole);
			admin2.setRole(adminRoles);
			userRepository.save(admin2);
		} else {
			admin2.setUserPassword(getEncodedPassword("admin321"));
			userRepository.save(admin2);
		}

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
	}

	public User registerNewUser(SignUpRequest signUpRequest) {
		// Create new user's account
		Role role = roleRepository.findById("User").get();
		Set<Role> userRoles = new HashSet<>();
		User user = new User(signUpRequest.getUserName(), signUpRequest.getUserFirstName(),
				signUpRequest.getUserLastName(), getEncodedPassword(signUpRequest.getUserPassword()));

		userRoles.add(role);
		user.setRole(userRoles);

		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		return users;
	}

	public void deleteUser(String userName) {
		System.out.println("userName: " + userName);
		userRepository.deleteByUserName(userName);
	}

	public User getUser(String userName) {

		return userRepository.findByUserName(userName);
	}

	// only can update userFirstName, userLastName, uerPassword
	public void updateUser(String userName, User user) {
//		user.setUserFirstName(user.getUserFirstName());
//		user.setUserLastName(user.getUserLastName());
		if (userName.equals(user.getUserName())) { // same userName
//			Role role = roleRepository.findById("User").get();
//			Set<Role> userRoles = new HashSet<>();
//			userRoles.add(role);
//			user.setRole(userRoles);
			user.setUserPassword(getEncodedPassword(user.getUserPassword()));
			userRepository.save(user);
		}
	}

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

}