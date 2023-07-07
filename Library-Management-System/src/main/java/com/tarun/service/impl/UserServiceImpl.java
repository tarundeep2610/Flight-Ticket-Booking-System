package com.tarun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarun.entites.User;
import com.tarun.repositories.UserRepository;
import com.tarun.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository repository;

	@Override
	public void registerUser(User user) {
		repository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return repository.findByUsername(username);
	}

}
