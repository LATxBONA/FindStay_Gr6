package com.example.PHONGTROSPRING.response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ListingsResponse {

	private int itemId;
	private String title;
	private BigDecimal price;
	private LocalDateTime createdAt;
	private String imageUrl;
	private String roomType;
	private String location_city;
	private String location_district;
	private String location_ward;
	private String address;
	private String user_name;
	private String phone;
	private int postType;
	private BigDecimal area;

	public ListingsResponse(int itemId, String title, BigDecimal price, LocalDateTime createdAt, String roomType,
			String location_city, String location_district, String location_ward, String address) {
		this.itemId = itemId;
		this.title = title;
		this.price = price;
		this.createdAt = createdAt;
		this.roomType = roomType;
		this.location_city = location_city;
		this.location_district = location_district;
		this.location_ward = location_ward;
		this.address = address;
	}

	public ListingsResponse(int itemId, String title, BigDecimal price, LocalDateTime createdAt, String roomType,
			String location_city, String location_district, String location_ward, String address, String user_name,
			String phone, int postType, BigDecimal area) {
		this.itemId = itemId;
		this.title = title;
		this.price = price;
		this.createdAt = createdAt;
		this.roomType = roomType;
		this.location_city = location_city;
		this.location_district = location_district;
		this.location_ward = location_ward;
		this.address = address;
		this.user_name = user_name;
		this.phone = phone;
		this.postType = postType;
		this.area = area;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public int getPostType() {
		return postType;
	}

	public void setPostType(int postType) {
		this.postType = postType;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getLocation_city() {
		return location_city;
	}

	public void setLocation_city(String location_city) {
		this.location_city = location_city;
	}

	public String getLocation_district() {
		return location_district;
	}

	public void setLocation_district(String location_district) {
		this.location_district = location_district;
	}

	public String getLocation_ward() {
		return location_ward;
	}

	public void setLocation_ward(String location_ward) {
		this.location_ward = location_ward;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String changeDate() {
		LocalDateTime now = LocalDateTime.now();
		long timeSeconds = ChronoUnit.SECONDS.between(createdAt, now);
		long timeMinutes = ChronoUnit.MINUTES.between(createdAt, now);
		long timeHours = ChronoUnit.HOURS.between(createdAt, now);
		long timeDays = ChronoUnit.DAYS.between(createdAt, now);

		String dateTime = "";
		if (timeSeconds <= 60) {
			dateTime = timeSeconds + " giây trước";
		} else if (timeMinutes <= 60) {
			dateTime = timeMinutes + " phút trước";
		} else if (timeHours <= 24) {
			dateTime = timeHours + " giờ trước";
		} else {
			dateTime = timeDays + " ngày trước";
		}

		return dateTime;
	}

	public String addressCurrent() {
		return address + ", " + location_ward + ", " + location_district + ", " + location_city;
	}

	public String changePrice() {
		if (price.compareTo(new BigDecimal("1000000")) > 0) {
			BigDecimal priceInMillions = price.divide(new BigDecimal("1000000"), 2, RoundingMode.HALF_UP);
			return priceInMillions + " triệu/tháng";
		} else {
			return price + " đồng/tháng";
		}
	}
}
