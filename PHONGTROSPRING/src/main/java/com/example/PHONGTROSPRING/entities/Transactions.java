package com.example.PHONGTROSPRING.entities;

import java.math.BigDecimal;
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
@Table(name = "Transactions")
public class Transactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionId;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
	private User user;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;

	private LocalDateTime transactionDate;

	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String status;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate() {
		this.transactionDate = LocalDateTime.now();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
	    String statusTemp = "Đang xử lý";

	    if ("Thành công".equals(status)) {
	        statusTemp = "Thành công";
	    } else if ("Thất bại".equals(status)) {
	        statusTemp = "Thất bại";
	    }

	    this.status = statusTemp;
	}

}
