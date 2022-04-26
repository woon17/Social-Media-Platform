package com.dxc.smp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.smp.entity.User;
import com.dxc.smp.service.UserService;

import java.util.List;

import javax.annotation.PostConstruct;

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
	
//	@GetMapping({"/deleteUser/{id}"})
//    @PreAuthorize("hasRole('Admin')")
//    public void deleteUserById(){
//		userService.
//    }


}