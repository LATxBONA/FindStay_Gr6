package com.example.PHONGTROSPRING.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.ChiTietHoaDon;
import com.example.PHONGTROSPRING.repository.ChiTietHoaDonRepository;

@Service
public class ChiTietHoaDonService {

	@Autowired
	private ChiTietHoaDonRepository chiTietHoaDonRepository;

	public List<ChiTietHoaDon> layAllCthd() {
		return chiTietHoaDonRepository.findAll();
	}

	public Optional<ChiTietHoaDon> layChiTietHoaDonTheoId(int id) {
		return chiTietHoaDonRepository.findById(id);
	}

	public List<ChiTietHoaDon> layAllByHoaDonId(int id) {
		return chiTietHoaDonRepository.getAllByHoaDonId(id);
	}

	private void calculateAmounts(ChiTietHoaDon chiTietHoaDon) {
        String tenKhoanThu = chiTietHoaDon.getTenKhoanThu().toLowerCase();
        
        if (tenKhoanThu.contains("điện") || tenKhoanThu.contains("nước")) {
            // For utility charges, calculate quantity based on meter readings
            if (chiTietHoaDon.getChiSoCuoi() != null && chiTietHoaDon.getChiSoDau() != null) {
                chiTietHoaDon.setSoLuong(chiTietHoaDon.getChiSoCuoi().subtract(chiTietHoaDon.getChiSoDau()));
            } else {
                chiTietHoaDon.setSoLuong(BigDecimal.ZERO);
            }
        } else {
            // For fixed charges, use quantity of 1 and no meter readings
            chiTietHoaDon.setChiSoDau(BigDecimal.ZERO);
            chiTietHoaDon.setChiSoCuoi(BigDecimal.ZERO);
            chiTietHoaDon.setSoLuong(BigDecimal.ONE);
        }
        
        // Calculate total amount
        if (chiTietHoaDon.getDonGia() != null && chiTietHoaDon.getSoLuong() != null) {
            chiTietHoaDon.setThanhTien(chiTietHoaDon.getDonGia().multiply(chiTietHoaDon.getSoLuong()));
        } else {
            chiTietHoaDon.setThanhTien(BigDecimal.ZERO);
        }
    }

    public ChiTietHoaDon taoChiTietHoaDon(ChiTietHoaDon chiTietHoaDon, int id) {
        String tenKhoanThu = chiTietHoaDon.getTenKhoanThu().toLowerCase();
        calculateAmounts(chiTietHoaDon);
        return chiTietHoaDonRepository.save(chiTietHoaDon);
    }

    public ChiTietHoaDon capNhatChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        Optional<ChiTietHoaDon> existingChiTietHoaDon = chiTietHoaDonRepository.findById(chiTietHoaDon.getId());
        if (existingChiTietHoaDon.isPresent()) {
            calculateAmounts(chiTietHoaDon);
            return chiTietHoaDonRepository.save(chiTietHoaDon);
        }
        return null;
    }


	// Xóa chi tiết hóa đơn
	public void xoaChiTietHoaDon(int id) {
		chiTietHoaDonRepository.deleteById(id);
	}

}
