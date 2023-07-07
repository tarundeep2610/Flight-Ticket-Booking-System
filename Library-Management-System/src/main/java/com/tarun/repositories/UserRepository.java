package com.tarun.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tarun.entites.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

}
