package com.dxc.smp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.smp.entity.User;
import com.dxc.smp.service.UserService;

import java.util.List;


@RestController
public class AdminController {

	@Autowired
	private UserService userService;

//    @PostConstruct
//    public void initRoleAndUser() {
//        userService.initRoleAndUser();
//    }


	@GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public List<User> forAdmin(){
        return userService.getAllUsers();
    }
	
	@PostMapping({"/deleteUser/{userName}"})
    @PreAuthorize("hasRole('Admin')")
    public void deleteUserByName(@PathVariable("userName") String userName){
		System.out.println("admin controller: " + userName);
		userService.deleteUser(userName);
    }
	
	@PostMapping({"/updateUser/{userName}"})
    @PreAuthorize("hasRole('Admin')")
    public void deleteUserByName(@PathVariable("userName") String userName,  @RequestBody User user){
		userService.updateUser(userName, user);
    }
}