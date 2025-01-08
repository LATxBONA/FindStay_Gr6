package com.example.PHONGTROSPRING.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findByphoneNumberAndPassword(String phone, String password);

	User findByuserId(String id);

	boolean existsByphoneNumber(String phone);

	@Query("SELECT u FROM User u " + "WHERE (:role IS NULL OR u.role = :role)")
	List<User> findUser(String role);
}
