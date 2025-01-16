package com.example.PHONGTROSPRING.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.HoaDon;
import com.example.PHONGTROSPRING.entities.ChiTietHoaDon;
import com.example.PHONGTROSPRING.repository.ChiTietHoaDonRepository;
import com.example.PHONGTROSPRING.repository.HoaDonRepository;

@Service
public class HoaDonService {

	@Autowired
	private HoaDonRepository hoaDonRepository;

	@Autowired
	private ChiTietHoaDonRepository chiTietHoaDonRepository;

	// Tạo hóa đơn mới
	public HoaDon taoHoaDon(HoaDon hoaDon) {
		hoaDon.setNgayTaoHoaDon(LocalDateTime.now());
		hoaDon.setDaThanhToan(false);
		return hoaDonRepository.save(hoaDon);
	}

	// Hàm tính và cập nhật tổng tiền
    public void tinhVaCapNhatTongTien(int hoaDonId) {
        Optional<HoaDon> hd = hoaDonRepository.findById(hoaDonId);
        if (hd.isPresent()) {
            HoaDon hoaDon = hd.get();
            List<ChiTietHoaDon> danhSachChiTiet = chiTietHoaDonRepository.getAllByHoaDonId(hoaDonId);
            
            BigDecimal tongTien = BigDecimal.ZERO;
            for (ChiTietHoaDon chiTiet : danhSachChiTiet) {
                if (chiTiet.getThanhTien() != null) {
                    tongTien = tongTien.add(chiTiet.getThanhTien());
                }
            }
            
            hoaDon.setTongTien(tongTien);
            hoaDonRepository.save(hoaDon);
        }
    }
	
	// Đánh dấu hóa đơn đã thanh toán
	public HoaDon thanhToanHoaDon(int hoaDonId) {
		Optional<HoaDon> hoaDon = hoaDonRepository.findById(hoaDonId);
		if (hoaDon.isPresent()) {
			HoaDon hd = hoaDon.get();
			List<ChiTietHoaDon> chiTietList = chiTietHoaDonRepository.getAllByHoaDonId(hoaDonId);

			tinhVaCapNhatTongTien(hd.getId());
			hd.setDaThanhToan(true);

			return hoaDonRepository.save(hd);
		}
		return null;
	}

	// Lấy tất cả hóa đơn
	public List<HoaDon> layTatCaHoaDon() {
		return hoaDonRepository.findAll();
	}

	// Lấy hóa đơn theo ID
	public Optional<HoaDon> layHoaDonTheoId(int id) {
		return hoaDonRepository.findById(id);
	}
	
	public Optional<HoaDon> layHoaDonTheoPhongId(int phongId) {
        return hoaDonRepository.findByPhongTroId(phongId);
    }

	public HoaDon capNhatHoaDon(HoaDon hoaDon) {
		Optional<HoaDon> existingHoaDon = hoaDonRepository.findById(hoaDon.getId());
		if (existingHoaDon.isPresent()) {
			HoaDon updatedHoaDon = existingHoaDon.get();
			updatedHoaDon.setNgayTaoHoaDon(hoaDon.getNgayTaoHoaDon());
			updatedHoaDon.setTongTien(hoaDon.getTongTien());
			updatedHoaDon.setDaThanhToan(hoaDon.isDaThanhToan());
			updatedHoaDon.setPhongTro(hoaDon.getPhongTro());
			return hoaDonRepository.save(updatedHoaDon);
		}
		return null; // Or throw an exception if the invoice doesn't exist
	}

	public void xoaHoaDon(int id) {
		Optional<HoaDon> hoaDonOpt = hoaDonRepository.findById(id);

		if (hoaDonOpt.isPresent()) {
			HoaDon hoaDon = hoaDonOpt.get();

			// Kiểm tra nếu hóa đơn đã thanh toán
			if (hoaDon.isDaThanhToan()) {
				throw new IllegalStateException("Không thể xóa hóa đơn đã thanh toán");
			}

			// Xóa tất cả chi tiết hóa đơn liên quan
			List<ChiTietHoaDon> chiTietList = chiTietHoaDonRepository.getAllByHoaDonId(hoaDon.getId());
			if (chiTietList != null && !chiTietList.isEmpty()) {
				chiTietHoaDonRepository.deleteAll(chiTietList);
			}

			// Xóa hóa đơn
			hoaDonRepository.delete(hoaDon);
		} else {
			throw new RuntimeException("Không tìm thấy hóa đơn với ID: " + id);
		}
	}
	
	public void deleteChiTiet(int id) {
		chiTietHoaDonRepository.deleteByHoaDonId(id);;
	}
}
