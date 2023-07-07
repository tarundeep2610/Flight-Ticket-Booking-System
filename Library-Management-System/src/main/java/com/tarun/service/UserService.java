package com.tarun.service;

import com.tarun.entites.User;

public interface UserService {

	void registerUser(User user);

	User findByUsername(String username);


}
