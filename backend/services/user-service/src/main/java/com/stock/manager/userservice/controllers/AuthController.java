package com.stock.manager.userservice.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stock.manager.userservice.payload.request.LoginRequest;
import com.stock.manager.userservice.payload.request.SignupRequest;
import com.stock.manager.userservice.security.services.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {	
	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return authService.register(signUpRequest);
	}
}
