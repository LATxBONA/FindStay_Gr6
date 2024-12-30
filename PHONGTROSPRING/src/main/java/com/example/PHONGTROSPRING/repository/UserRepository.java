package com.example.PHONGTROSPRING.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	 User findByphoneNumberAndPassword(String phone,String password);
	 User findByuserId(String id);
	 boolean existsByphoneNumber(String phone);
}
