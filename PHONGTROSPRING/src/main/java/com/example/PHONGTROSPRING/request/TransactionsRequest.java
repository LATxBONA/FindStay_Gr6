package com.example.PHONGTROSPRING.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionsRequest {

	private Integer transactionId;
	private String userFullName = "";
	private BigDecimal amount;
	private LocalDateTime transactionDate;
	private String status = "";
	
	public TransactionsRequest(String userFullName, BigDecimal amount, LocalDateTime transactionDate, String status) {
		this.userFullName = userFullName;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.status = status;
	}
	
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
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
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
