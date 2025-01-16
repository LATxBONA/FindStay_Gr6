package com.example.PHONGTROSPRING.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class ThongTinNguoiThue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "phong_tro_id")
	private PhongTro phongTro;

	private String hoTen;
	private String sDT;
	private String soCCCD;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PhongTro getPhongTro() {
		return phongTro;
	}

	public void setPhongTro(PhongTro phongTro) {
		this.phongTro = phongTro;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getsDT() {
		return sDT;
	}

	public void setsDT(String sDT) {
		this.sDT = sDT;
	}

	public String getSoCCCD() {
		return soCCCD;
	}

	public void setSoCCCD(String soCCCD) {
		this.soCCCD = soCCCD;
	}

}
