package com.example.model.dao;

import com.example.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = ?1 and u.password = ?2")
	public User findByEmailAndPassword(String email,String password);

	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);

}
