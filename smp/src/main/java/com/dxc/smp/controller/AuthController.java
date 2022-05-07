package com.dxc.smp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.smp.payload.request.LoginRequest;
import com.dxc.smp.payload.request.SignUpRequest;
import com.dxc.smp.payload.response.JwtResponse;
import com.dxc.smp.payload.response.MessageResponse;
import com.dxc.smp.service.JwtService;
import com.dxc.smp.service.UserService;

import org.springframework.http.ResponseEntity;


@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/v0")
public class AuthController {
	@Autowired
	private JwtService jwtService;

	@Autowired
	UserService userService;

	// used for login
	@PostMapping({ "/signin" })
	public ResponseEntity<?> createJwtToken(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
		
		try {
			JwtResponse jwsResponse = jwtService.createJwtToken(loginRequest);
			return ResponseEntity.ok(jwsResponse);
		} catch (Exception e) {
			if (userService.getUser(loginRequest.getUserName()) == null) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Username is not exising!"));
			}else{
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: password is not matched!"));
			}
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userService.getUser(signUpRequest.getUserName()) != null) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		} else {
			userService.registerNewUser(signUpRequest);
			return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		}
	}
}

