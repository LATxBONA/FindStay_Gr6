package com.example.PHONGTROSPRING.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String userId;

	@Column(nullable = true, length = 15)
	private String password;

	@Column(nullable = true, length = 100)
	private String email;

	@Column(nullable = true, length = 100, columnDefinition = "varchar(100)")
	private String fullName;

	@Column(nullable = true, length = 11)
	private String phoneNumber;

	@Column(nullable = true, length = 15, columnDefinition = "varchar(50)")
	private String role;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal balance;

	@Column(nullable = true)
	private LocalDateTime createdAt;
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		String roleTemp = "Tìm kiếm";

		if (role.equals("Quản trị viên")) {
			roleTemp = "Quản trị viên";
		} else if (role.equals("Chủ trọ")) {
			roleTemp = "Chủ trọ";
		}

		this.role = roleTemp;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt() {
		this.createdAt = LocalDateTime.now();
	}
}
