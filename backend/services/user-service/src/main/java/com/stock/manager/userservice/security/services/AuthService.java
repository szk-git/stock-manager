package com.stock.manager.userservice.security.services;

import org.springframework.http.ResponseEntity;

import com.stock.manager.userservice.payload.request.LoginRequest;
import com.stock.manager.userservice.payload.request.SignupRequest;
import com.stock.manager.userservice.payload.response.MessageResponse;

public interface AuthService {
	public ResponseEntity<?> login(LoginRequest loginRequest);
	public ResponseEntity<MessageResponse> register(SignupRequest signUpRequest);
}
