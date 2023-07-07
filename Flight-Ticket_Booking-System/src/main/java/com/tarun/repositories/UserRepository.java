package com.tarun.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tarun.entites.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String Username);

}
