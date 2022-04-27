package com.dxc.smp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.smp.entity.JwtRequest;
import com.dxc.smp.entity.JwtResponse;
import com.dxc.smp.entity.User;
import com.dxc.smp.repository.UserRepository;
import com.dxc.smp.service.JwtService;
import com.dxc.smp.service.UserService;
import com.dxc.smp.util.JwtUtil;

import org.springframework.http.ResponseEntity;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import java.util.stream.Collectors;
@RestController
@CrossOrigin
public class JwtController {
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
	public ResponseEntity<?> createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		
		try {
			JwtResponse jwsResponse = jwtService.createJwtToken(jwtRequest);
			return ResponseEntity.ok(jwsResponse);
		} catch (Exception e) {
			if (userService.getUser(jwtRequest.getUserName()) == null) {
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
	public ResponseEntity<?> registerUser(@RequestBody User signUpUser) {
		if (userService.getUser(signUpUser.getUserName()) != null) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		} else {
			userService.registerNewUser(signUpUser);
			return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		}
	}
}

class MessageResponse {
	private String message;

	public MessageResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
