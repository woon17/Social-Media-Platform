package com.dxc.smp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.smp.payload.request.LoginRequest;
import com.dxc.smp.payload.request.SignUpRequest;
import com.dxc.smp.payload.response.JwtResponse;
import com.dxc.smp.payload.response.MessageResponse;
import com.dxc.smp.service.JwtService;
import com.dxc.smp.service.UserService;

import org.springframework.http.ResponseEntity;


@RestController
@CrossOrigin
public class AuthController {
	// @Autowired
	// AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;

	@Autowired
	UserService userService;

	// @Autowired
	// private JwtUtil jwtUtil;
	// used for login
	@PostMapping({ "/signin" })
	public ResponseEntity<?> createJwtToken(@RequestBody LoginRequest loginRequest) throws Exception {
		
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

		// return ResponseEntity
		// .badRequest()
		// .body(new MessageResponse("Error: Email is already in use!"));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
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

