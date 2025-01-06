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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="PaymentHistory")
public class PaymentHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
	@ManyToOne
	@JoinColumn(nullable = false, name ="userId", referencedColumnName = "userId")
	private User user;
	
	@Column(nullable = false)
	private LocalDateTime createAt;
	
	@Column(nullable = false, precision = 10, scale = 0)
	private BigDecimal paymentFee;
	
	@Column(nullable = false, precision = 10, scale = 0)
	private BigDecimal balanceBefore;
	
	@Column(nullable = false, precision = 10, scale = 0)
	private BigDecimal balanceAfter;
	
	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String typeActivity;
	
	@ManyToOne
	@JoinColumn(nullable = false, name="itemId", referencedColumnName = "itemId")
	private Listings listings;


	
	public PaymentHistory() {
		super();
	}

	public PaymentHistory(User user, LocalDateTime createAt, BigDecimal paymentFee, BigDecimal balanceBefore,
			BigDecimal balanceAfter, String typeActivity, Listings listings) {
		super();
		this.user = user;
		this.createAt = createAt;
		this.paymentFee = paymentFee;
		this.balanceBefore = balanceBefore;
		this.balanceAfter = balanceAfter;
		this.typeActivity = typeActivity;
		this.listings = listings;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public BigDecimal getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = paymentFee;
	}

	public BigDecimal getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(BigDecimal balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(BigDecimal balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public String getTypeActivity() {
		return typeActivity;
	}

	public void setTypeActivity(String typeActivity) {
		this.typeActivity = typeActivity;
	}

	public Listings getListings() {
		return listings;
	}

	public void setListings(Listings listings) {
		this.listings = listings;
	}
	
	
}
