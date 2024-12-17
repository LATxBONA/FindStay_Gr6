package com.example.PHONGTROSPRING.response;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

//import com.example.PHONGTROSPRING.entities.Locations;
import com.example.PHONGTROSPRING.entities.RoomTypes;
import com.example.PHONGTROSPRING.entities.User;

public class phongtroresponse {
	private int itemId;
	private User user;
	private String title;
	private String description;
	private BigDecimal price;
	private BigDecimal area;
//	private Locations location;
	private String address;
	private RoomTypes roomType;
	private LocalDateTime createdAt;
	private LocalDateTime expiryDate;
	private LocalDateTime updatedAt;
	private int postType;
	private String status;
	private String object;
	private List<String> listings;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getArea() {
		return area;
	}
	public void setArea(BigDecimal area) {
		this.area = area;
	}
//	public Locations getLocation() {
//		return location;
//	}
//	public void setLocation(Locations location) {
//		this.location = location;
//	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public RoomTypes getRoomType() {
		return roomType;
	}
	public void setRoomType(RoomTypes roomType) {
		this.roomType = roomType;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public int getPostType() {
		return postType;
	}
	public void setPostType(int postType) {
		this.postType = postType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public List<String> getListings() {
		return listings;
	}
	public void setListings(List<String> listings) {
		this.listings = listings;
	}
	public String getFormattedPrice() {
	    DecimalFormat decimalFormat = new DecimalFormat("#.##");

	    if (this.price.compareTo(BigDecimal.valueOf(1_000_000)) >= 0) {
	        // Nếu giá >= 1 triệu
	        BigDecimal million = this.price.divide(BigDecimal.valueOf(1_000_000));
	        return decimalFormat.format(million) + " triệu";
	    } else if (this.price.compareTo(BigDecimal.valueOf(1_000)) >= 0) {
	        // Nếu giá >= 1 nghìn
	        BigDecimal thousand = this.price.divide(BigDecimal.valueOf(1_000));
	        return decimalFormat.format(thousand) + "k";
	    } else {
	        // Giá nhỏ hơn 1 nghìn
	        return this.price + " VND";
	    }
	}
	public String getRelativeTime() {
	    LocalDateTime now = LocalDateTime.now();
	    Duration duration = Duration.between(this.createdAt, now);

	    if (duration.toHours() < 24) {
	        return duration.toHours() + " giờ trước";
	    } else if (duration.toDays() <= 30) {
	        return duration.toDays() + " ngày trước";
	    } else {
	        long months = ChronoUnit.MONTHS.between(this.createdAt.toLocalDate(), now.toLocalDate());
	        return months + " tháng trước";
	    }
	}
	
	
}
