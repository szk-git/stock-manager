package com.stock.manager.userservice.controllers;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stock.manager.userservice.models.RoleEnum;
import com.stock.manager.userservice.models.Role;
import com.stock.manager.userservice.models.User;
import com.stock.manager.userservice.payload.request.LoginRequest;
import com.stock.manager.userservice.payload.request.SignupRequest;
import com.stock.manager.userservice.payload.response.JwtResponse;
import com.stock.manager.userservice.payload.response.MessageResponse;
import com.stock.manager.userservice.repository.RoleRepository;
import com.stock.manager.userservice.repository.UserRepository;
import com.stock.manager.userservice.security.jwt.JwtUtils;
import com.stock.manager.userservice.security.services.UserDetailsImpl;


@RestController
@RequestMapping("/auth")
public class AuthController {
	Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());
		
		User userById = userRepository.getOne(userDetails.getId());
		userById.setLastLogin(new Date());
		userRepository.save(userById);

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User(signUpRequest.getUsername(), signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
