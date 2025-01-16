package com.example.PHONGTROSPRING.entities;

import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class PhongTro {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String tenPhong;
    
    @ManyToOne
    @JoinColumn(name = "itemId")
    private Listings listings;
    
    @OneToMany(mappedBy = "phongTro")
    private List<ThongTinNguoiThue> thongTinNguoiThue;
    
    private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTenPhong() {
		return tenPhong;
	}

	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}

	public Listings getListings() {
		return listings;
	}

	public void setListings(Listings listings) {
		this.listings = listings;
	}

	public List<ThongTinNguoiThue> getThongTinNguoiThue() {
		return thongTinNguoiThue;
	}

	public void setThongTinNguoiThue(List<ThongTinNguoiThue> thongTinNguoiThue) {
		this.thongTinNguoiThue = thongTinNguoiThue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}
