package com.example.PHONGTROSPRING.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.text.DecimalFormat;

@Entity
@Table(name = "Listings")
public class Listings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int itemId;

	@ManyToOne
	@JoinColumn(nullable = false, name = "userId", referencedColumnName = "userId")
	private User user;

	@Column(nullable = false, length = 100, columnDefinition = "varchar(255)")
	private String title;

	@Column(nullable = false, length = 5000, columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false, precision = 10, scale = 0)
	private BigDecimal price;

	@Column(nullable = false, precision = 5, scale = 0)
	private BigDecimal area;

	@ManyToOne
	@JoinColumn(nullable = false, name = "city_id", referencedColumnName = "city_id")
	private LocationsCity location_city;

	@ManyToOne
	@JoinColumn(nullable = false, name = "district_id", referencedColumnName = "district_id")
	private LocationsDistrict location_district;

	@ManyToOne
	@JoinColumn(nullable = false, name = "ward_id", referencedColumnName = "ward_id")
	private LocationsWard location_ward;

	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String address;

	@ManyToOne
	@JoinColumn(nullable = false, name = "roomTypeId", referencedColumnName = "roomTypeId")
	private RoomTypes roomType;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = true)
	private LocalDateTime expiryDate;

	// @Column(nullable = true)
	private LocalDateTime updatedAt;

	@Column(name = "postType")
	private int postType;

	@Column(nullable = false, columnDefinition = "nvarchar(255)")
	private String status;

	@Column(nullable = false, columnDefinition = "nvarchar(255)")
	private String object;

	
	
	/*
	 * public LocalDateTime getExpiryDate() { return expiryDate; }
	 * 
	 * public void setExpiryDate(int vip) { if (vip == 5) { expiryDate =
	 * LocalDateTime.now().plusDays(5); } }
	 */

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public LocationsCity getLocation_city() {
		return location_city;
	}

	public void setLocation_city(LocationsCity location_city) {
		this.location_city = location_city;
	}

	public LocationsDistrict getLocation_district() {
		return location_district;
	}

	public void setLocation_district(LocationsDistrict location_district) {
		this.location_district = location_district;
	}

	public LocationsWard getLocation_ward() {
		return location_ward;
	}

	public void setLocation_ward(LocationsWard location_ward) {
		this.location_ward = location_ward;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getPostType() {
		return postType;
	}

	public String changePostType() {
		if (postType == 1) {
			return "Tin thường";
		} else if (postType == 5) {
			return "Tin VIP nổi bật";
		} else {
			return "Tin VIP " + (5 - postType);
		}
	}

	public void setPostType(int postType) {
		this.postType = postType;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

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

	public String changePrice() {
		if (price.compareTo(new BigDecimal("1000000")) > 0) {
			BigDecimal priceInMillions = price.divide(new BigDecimal("1000000"), 2, RoundingMode.HALF_UP);
			return priceInMillions + " triệu/tháng";
		} else {
			return price + " đồng/tháng";
		}
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

	public String date(LocalDateTime date) {
		LocalDateTime now = LocalDateTime.now();
		long timeSeconds = ChronoUnit.SECONDS.between(date, now);
		long timeMinutes = ChronoUnit.MINUTES.between(date, now);
		long timeHours = ChronoUnit.HOURS.between(date, now);
		long timeDays = ChronoUnit.DAYS.between(date, now);

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

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public RoomTypes getRoomType() {
		return roomType;
	}

	// Phương thức getter để lấy tên loại phòng
	public String getRoomTypeName() {
		return roomType != null ? roomType.getRoomTypeName() : "Không xác định"; // Trả về "Không xác định" nếu roomType
																					// là null
	}

	public void setRoomType(RoomTypes roomType) {
		this.roomType = roomType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getFormattedCreatedAt() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy -  HH:mm");
		return this.createdAt.format(formatter);
	}

	public void setCreatedAt() {
		this.createdAt = LocalDateTime.now();
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt() {
		this.updatedAt = LocalDateTime.now();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		String statusTemp = "Chờ duyệt";

		if (status.equals("Đã duyệt")) {
			statusTemp = "Đã duyệt";
		} else if (status.equals("Chờ duyệt")) {
			statusTemp = "Chờ duyệt";
		} else if (status.equals("Ẩn tin")) {
			statusTemp = "Ẩn tin";
		}

		this.status = statusTemp;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		String objectTemp = "Tất cả";

		if (object.equals("Nam")) {
			objectTemp = "Nam";
		} else if (object.equals("Nữ")) {
			objectTemp = "Nữ";
		}

		this.object = objectTemp;
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
		return address + ", " + location_ward.getWard() + ", " + location_district.getDistrict() + ", "
				+ location_city.getCity();
	}
}
