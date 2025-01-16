package com.example.PHONGTROSPRING.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ChiTietHoaDon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tenKhoanThu; // Tên các khoản thu (điện, nước, internet...)
	private BigDecimal donGia; // Đơn giá của khoản thu
	private BigDecimal chiSoDau; // Chỉ số đầu (dùng cho điện, nước nếu có)
	private BigDecimal chiSoCuoi; // Chỉ số cuối
	private BigDecimal soLuong; // Số lượng/đơn vị tính
	private BigDecimal thanhTien; // Thành tiền của khoản thu

	@ManyToOne
	@JoinColumn(name = "hoa_don_id")
	private HoaDon hoaDon;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTenKhoanThu() {
		return tenKhoanThu;
	}

	public void setTenKhoanThu(String tenKhoanThu) {
		this.tenKhoanThu = tenKhoanThu;
	}

	public BigDecimal getDonGia() {
		return donGia;
	}

	public void setDonGia(BigDecimal donGia) {
		this.donGia = donGia;
	}

	public BigDecimal getChiSoDau() {
		return chiSoDau;
	}

	public void setChiSoDau(BigDecimal chiSoDau) {
		this.chiSoDau = chiSoDau;
	}

	public BigDecimal getChiSoCuoi() {
		return chiSoCuoi;
	}

	public void setChiSoCuoi(BigDecimal chiSoCuoi) {
		this.chiSoCuoi = chiSoCuoi;
	}

	public BigDecimal getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(BigDecimal soLuong) {
		this.soLuong = soLuong;
	}

	public BigDecimal getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien(BigDecimal thanhTien) {
		this.thanhTien = thanhTien;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	

}
