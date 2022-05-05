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

	@Autowired
	private FilesStorageService filesStorageService;

	// set two default admin with default password when running the application
	public void initAdmins() {

		User admin1 = getUser("admin123");
		if (admin1 == null) {
			admin1 = new User();
			admin1.setUserName("admin123");
			admin1.setUserPassword(getEncodedPassword("admin123"));
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
		Set<Role> adminRoles = new HashSet<>();
		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		adminRoles.add(adminRole);
//		List<User> users = (List<User>) userRepository.findByRoles(adminRoles);
		List<User> users = (List<User>) userRepository.findAll();
		return users;
	}

	public void deleteUser(String userName) {
		System.out.println("userName: " + userName);
		User user = getUser(userName);
		for(Role r :user.getRole()) {
			if(r.getRoleName().equals("Admin")) {
				return;
			}
		}
		filesStorageService.deleteUserByUserName(userName);
		userRepository.deleteByUserName(userName);
	}

	public User getUser(String userName) {

		return userRepository.findByUserName(userName);
	}

	// only can update userFirstName, userLastName, uerPassword
	public void updateUser(String userName, User user) {
//		user.setUserFirstName(user.getUserFirstName());
//		user.setUserLastName(user.getUserLastName());
		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		
		for(Role r :user.getRole()) {
			if(r.getRoleName().equals("Admin")) {
				return;
			}
		}
		
		if(userName.equals(user.getUserName())) { // same userName
			System.out.println("Updating a user...");
			user.setUserPassword(getEncodedPassword(user.getUserPassword()));
			userRepository.save(user);
		}
	}

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

}