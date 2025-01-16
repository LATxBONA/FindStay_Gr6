package com.example.PHONGTROSPRING.entities;

import java.util.Base64;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Images")
public class Images {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int imageId;

	@ManyToOne
	@JoinColumn(name = "itemId", referencedColumnName = "itemId")
	private Listings listing;

	//@Column(nullable = false, length = 255)
	@Lob
	private byte[] imageUrl;

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public Listings getListing() {
		return listing;
	}

	public void setListing(Listings listing) {
		this.listing = listing;
	}

	public byte[] getImageUrl() {
		return imageUrl;
	}
	
    public String getImageUrlAsBase64() {
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageUrl);
    }

	public void setImageUrl(byte[] imageUrl) {
		this.imageUrl = imageUrl;
	}
	

}
