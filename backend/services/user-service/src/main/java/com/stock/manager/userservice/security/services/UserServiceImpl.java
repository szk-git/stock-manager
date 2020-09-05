package com.stock.manager.userservice.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stock.manager.userservice.models.User;
import com.stock.manager.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	PasswordEncoder encoder;
	
	private final String ADMIN = "ROLE_ADMIN";
	private final String MODERATOR = "ROLE_MODERATOR";
	
	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public List<User> getUsersByName(String username) {
		return userRepository.findByNameContainingIgnoreCase(username);
	}
	
	@Override
	public void editUsername(Long id,  String username) {
		User user = userDetailsServiceImpl.loadUserById(id);
		if(isUserAdmin() || isUserModerator() || isUserSame(user)) {
			user.setUsername(username);
			userRepository.save(user);
		}
	}
	
	@Override
	public void editUserPassword(Long id, String password) {
		User user = userDetailsServiceImpl.loadUserById(id);
		if(isUserAdmin() || isUserSame(user)) {
			user.setPassword(encoder.encode(password));
			userRepository.save(user);
		}
	}

	@Override
	public void editUserEmail(Long id, String email) {
		User user = userDetailsServiceImpl.loadUserById(id);
		if(isUserAdmin() || isUserModerator() || isUserSame(user)) {
			user.setEmail(email);
			userRepository.save(user);
		}
	}

	@Override
	public void editUserFirstname(Long id, String firstName) {
		User user = userDetailsServiceImpl.loadUserById(id);
		if(isUserAdmin() || isUserModerator() || isUserSame(user)) {
			user.setFirstName(firstName);
			userRepository.save(user);
		}
	}

	@Override
	public void editUserLastname(Long id, String lastName) {
		User user = userDetailsServiceImpl.loadUserById(id);
		if(isUserAdmin() || isUserModerator() || isUserSame(user)) {
			user.setLastName(lastName);
			userRepository.save(user);
		}
	}

	@Override
	public boolean isUserDeleted(Long id) {
		User user = userDetailsServiceImpl.loadUserById(id);
		if(isUserAdmin() || isUserSame(user)){
			userRepository.delete(user);
			return true;
		}
		return false;
	}
	
	private boolean isUserAdmin() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(ADMIN));
	}
	
	private boolean isUserModerator() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(MODERATOR));
	}
	
	private boolean isUserSame(User user) {
		return user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName());
	}

}
