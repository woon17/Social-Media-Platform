package com.dxc.smp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.smp.entity.User;
import com.dxc.smp.payload.request.LoginRequest;
import com.dxc.smp.payload.request.UpdateUserRequest;
import com.dxc.smp.service.UserService;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/v0")
@PreAuthorize("hasRole('Admin')")
public class AdminController {

	@Autowired
	private UserService userService;

    @PostConstruct
    public void initAdmins() {
		System.out.println("running initAdmins");
        userService.initAdmins();
    }

	@GetMapping({ "/forAdmin" })
		public List<User> forAdmin() {
		return userService.getAllUsers();
	}
	
	@GetMapping({ "/getUser/{userName}" })
	public User getUser(@PathVariable("userName") String userName) {
		return userService.getUser(userName);
	}

	@PutMapping({ "/updateUser/{userName}" })
	public void updateUser(@PathVariable("userName") String userName, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
		userService.updateUser(userName, updateUserRequest);
	}
	
	@DeleteMapping({ "/deleteUser/{userName}" })
	public void deleteUserByName(@PathVariable("userName") String userName) {
		System.out.println("admin controller: " + userName);
		userService.deleteUser(userName);
	}
}