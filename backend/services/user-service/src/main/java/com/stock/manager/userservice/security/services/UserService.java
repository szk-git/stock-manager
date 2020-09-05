package com.stock.manager.userservice.security.services;

import java.util.List;

import com.stock.manager.userservice.models.User;

public interface UserService {
	public List<User> getAllUser();
	public List<User> getUsersByName(String username);
	public void editUsername(Long id,  String username);
	public void editUserPassword(Long id, String password);
	public void editUserEmail(Long id, String email);
	public void editUserFirstname(Long id, String firstName);
	public void editUserLastname(Long id, String lastName);
	public boolean isUserDeleted(Long id);
}
