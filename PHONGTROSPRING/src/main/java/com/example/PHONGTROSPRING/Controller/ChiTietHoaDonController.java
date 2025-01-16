package com.example.PHONGTROSPRING.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.PHONGTROSPRING.entities.ChiTietHoaDon;
import com.example.PHONGTROSPRING.entities.HoaDon;
import com.example.PHONGTROSPRING.service.ChiTietHoaDonService;
import com.example.PHONGTROSPRING.service.HoaDonService;

@Controller
@RequestMapping("/chitiet-hoadon")
public class ChiTietHoaDonController {

	@Autowired
	private ChiTietHoaDonService chiTietHoaDonService;

	@Autowired
	private HoaDonService hoaDonService;

	@GetMapping("/them")
	public String hienThiFormThem(@RequestParam int hoaDonId, Model model) {
		ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
		Optional<HoaDon> hoaDon = hoaDonService.layHoaDonTheoId(hoaDonId);
		if (hoaDon.isPresent()) {
			chiTietHoaDon.setHoaDon(hoaDon.get());
			model.addAttribute("chiTietHoaDon", chiTietHoaDon);
			return "views/hoadon/chitiet-khoan-thu";
		}
		return "redirect:/hoadon/danhsach";
	}

	@PostMapping("/them")
	public String themChiTietHoaDon(@ModelAttribute ChiTietHoaDon chiTietHoaDon) {
		try {
			// Lấy lại thông tin HoaDon từ database
			Optional<HoaDon> hoaDon = hoaDonService.layHoaDonTheoId(chiTietHoaDon.getHoaDon().getId());
			if (hoaDon.isPresent()) {
				chiTietHoaDon.setHoaDon(hoaDon.get());
				ChiTietHoaDon savedChiTiet = chiTietHoaDonService.taoChiTietHoaDon(chiTietHoaDon, hoaDon.get().getId());
				if (savedChiTiet != null) {
					hoaDonService.tinhVaCapNhatTongTien(hoaDon.get().getId());
					return "redirect:/hoadon/chitiet/" + hoaDon.get().getId();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/hoadon/danhsach";
	}

	@GetMapping("/sua/{id}")
	public String hienThiFormSua(@PathVariable int id, Model model) {
		Optional<ChiTietHoaDon> chiTietHoaDon = chiTietHoaDonService.layChiTietHoaDonTheoId(id);
		if (chiTietHoaDon.isPresent()) {
			model.addAttribute("chiTietHoaDon", chiTietHoaDon.get());
			return "views/hoadon/chitiet-khoan-thu";
		}
		return "redirect:/hoadon/danhsach";
	}

	@PostMapping("/sua")
	public String suaChiTietHoaDon(@ModelAttribute ChiTietHoaDon chiTietHoaDon) {
		chiTietHoaDonService.capNhatChiTietHoaDon(chiTietHoaDon);
		return "redirect:/hoadon/chitiet/" + chiTietHoaDon.getHoaDon().getId();
	}

	@GetMapping("/xoa/{id}")
	public String xoaChiTietHoaDon(@PathVariable int id) {
		Optional<ChiTietHoaDon> chiTietHoaDon = chiTietHoaDonService.layChiTietHoaDonTheoId(id);
		if (chiTietHoaDon.isPresent()) {
			int hoaDonId = chiTietHoaDon.get().getHoaDon().getId();
			chiTietHoaDonService.xoaChiTietHoaDon(id);
			return "redirect:/hoadon/chitiet/" + hoaDonId;
		}
		return "redirect:/hoadon/danhsach";
	}
}
