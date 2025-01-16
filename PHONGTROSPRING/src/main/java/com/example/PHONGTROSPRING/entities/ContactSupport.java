package com.example.PHONGTROSPRING.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ContactSupport")
public class ContactSupport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int supportId;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;

	@Column(nullable = false, length = 255, columnDefinition = "varchar(255)")
	private String subject;

	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String message;

	@Column(nullable = false, length = 20, columnDefinition = "varchar(255)")
	private String status;

	private LocalDateTime createdAt;

	public int getSupportId() {
		return supportId;
	}

	public void setSupportId(int supportId) {
		this.supportId = supportId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt() {
		this.createdAt = LocalDateTime.now();
	}
}
