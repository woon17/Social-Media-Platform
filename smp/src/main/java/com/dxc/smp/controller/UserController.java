package com.dxc.smp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.smp.entity.User;
import com.dxc.smp.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
//    @PostConstruct
//    public void initRoleAndUser() {
//        userService.initRoleAndUser();
//    }


//
//	@GetMapping({"/forAdmin"})
//    @PreAuthorize("hasRole('Admin')")
//    public List<User> forAdmin(){
//        return userService.getAllUsers();
//    }


}