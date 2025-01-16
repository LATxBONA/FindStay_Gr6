package com.example.PHONGTROSPRING.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Favorites")
public class Favorites {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int favoriteId;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;

	@ManyToOne
	@JoinColumn(name = "itemId", referencedColumnName = "itemId")
	private Listings listing;

	public int getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(int favoriteId) {
		this.favoriteId = favoriteId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Listings getListing() {
		return listing;
	}

	public void setListing(Listings listing) {
		this.listing = listing;
	}
}
