package com.example.PHONGTROSPRING.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.PHONGTROSPRING.entities.Locations;
import com.example.PHONGTROSPRING.entities.RoomTypes;
import com.example.PHONGTROSPRING.entities.User;

public class RequestPostNew {

	private String userid;
	private String title;
	private String description;
	private BigDecimal price;
	private BigDecimal area;
	private int locationid;
	private String address;
	private int roomTypeid;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String status;
	private String object;
	private List<MultipartFile> urlAnh;
	private MultipartFile urlVideo;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public int getLocationid() {
		return locationid;
	}

	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRoomTypeid() {
		return roomTypeid;
	}

	public void setRoomTypeid(int roomTypeid) {
		this.roomTypeid = roomTypeid;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
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

	public List<MultipartFile> getUrlAnh() {
		return urlAnh;
	}

	public void setUrlAnh(List<MultipartFile> urlAnh) {
		this.urlAnh = urlAnh;
	}

	/*
	 * public MultipartFile getUrlAnh() { return urlAnh; }
	 * 
	 * public void setUrlAnh(MultipartFile urlAnh) { this.urlAnh = urlAnh; }
	 */

	/*
	 * public MultipartFile getUrlVideo() { return urlVideo; }
	 * 
	 * public void setUrlVideo(MultipartFile urlVideo) { this.urlVideo = urlVideo; }
	 */

}
