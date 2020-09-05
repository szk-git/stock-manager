package com.stock.manager.userservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.manager.userservice.models.User;
import com.stock.manager.userservice.security.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<User> getAllUser(){
		return userService.getAllUser();
	}
	
	@GetMapping("/{username}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<User> getUsersIfContainName(@PathVariable String username){
		return userService.getUsersByName(username);
	}
	
	@PatchMapping("/{id}/username")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> updateUsername(@PathVariable Long id, @RequestParam String newname){
		userService.editUsername(id, newname);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Username succesfully changed!");
	}
	
	@PatchMapping("/{id}/password")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestParam String newpassword){
		userService.editUserPassword(id, newpassword);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password succesfully changed!");
	}
	
	@PatchMapping("/{id}/email")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> updateEmail(@PathVariable Long id, @RequestParam String newemail){
		userService.editUserEmail(id, newemail);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Email succesfully changed!");
	}
	
	@PatchMapping("/{id}/firstname")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> updateFirstname(@PathVariable Long id, @RequestParam String newfirstname){
		userService.editUserFirstname(id, newfirstname);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("First name succesfully changed!");
	}
	
	@PatchMapping("/{id}/lastname")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> updateLastname(@PathVariable Long id, @RequestParam String newlastname){
		userService.editUserLastname(id, newlastname);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Last name succesfully changed!");
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(Authentication authentication, @PathVariable Long id){
		if(!userService.isUserDeleted(id)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can't do this!");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("User succesfully deleted!");
	}
}
