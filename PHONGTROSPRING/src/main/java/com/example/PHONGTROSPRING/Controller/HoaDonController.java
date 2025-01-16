package com.example.PHONGTROSPRING.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import com.example.PHONGTROSPRING.service.PhongTroService;

@Controller
@RequestMapping("/hoadon")
public class HoaDonController {

	@Autowired
	private HoaDonService hoaDonService;

	@Autowired
	private ChiTietHoaDonService chiTietHoaDonService;

	@Autowired
	private PhongTroService phongTroService;

	@GetMapping("/danhsach")
	public String danhSachHoaDon(Model model) {
		model.addAttribute("danhSachHoaDon", hoaDonService.layTatCaHoaDon());
		return "views/hoadon/danhsach";
	}

	@GetMapping("/danhsach/{id}")
	public String danhSachHoaDon(Model model, @PathVariable int id) {
		Optional<HoaDon> hd = hoaDonService.layHoaDonTheoPhongId(id);

		HoaDon hoaDon;
		// Nếu không tìm thấy, tạo hóa đơn mới
		if (!hd.isPresent()) {
			hoaDon = new HoaDon();
			hoaDon.setPhongTro(phongTroService.getPhongTroById(id)); // Gán phòng trọ
			hoaDon.setNgayTaoHoaDon(LocalDateTime.now()); // Ngày tạo hóa đơn
			hoaDon.setTongTien(BigDecimal.ZERO); // Ban đầu tổng tiền là 0
			hoaDon.setDaThanhToan(false); // Chưa thanh toán

			// Lưu hóa đơn vào database
			hoaDon = hoaDonService.taoHoaDon(hoaDon);
		} else {
			hoaDon = hd.get(); // Nếu có hóa đơn, lấy từ Optional
		}

		// Thêm vào model để hiển thị
		model.addAttribute("hoaDon", hoaDon);
		return "redirect:/hoadon/chitiet/phong/" + hoaDon.getPhongTro().getId();
	}

	@GetMapping("/chitiet/{id}")
	public String chiTietHoaDon(@PathVariable int id, Model model) {
		Optional<HoaDon> hoaDon = hoaDonService.layHoaDonTheoId(id);
		if (hoaDon.isPresent()) {
			List<ChiTietHoaDon> cthdlist = chiTietHoaDonService.layAllByHoaDonId(hoaDon.get().getId());
			model.addAttribute("hoaDon", hoaDon.get());
			model.addAttribute("cthdlist", cthdlist);
			return "views/hoadon/chitiet-hoadon";
		}
		return "redirect:/hoadon/danhsach";
	}

	@GetMapping("/chitiet/phong/{phongId}")
	public String danhSachHoaDonTheoPhong(@PathVariable int phongId, Model model) {
		Optional<HoaDon> hoaDon = hoaDonService.layHoaDonTheoPhongId(phongId);
		if (hoaDon.isPresent()) {
			List<ChiTietHoaDon> cthdlist = chiTietHoaDonService.layAllByHoaDonId(hoaDon.get().getId());
			model.addAttribute("hoaDon", hoaDon.get());
			model.addAttribute("cthdlist", cthdlist);
			return "views/hoadon/chitiet-hoadon";
		}
		return "redirect:/hoadon/them";
	}

	@GetMapping("/them")
	public String hienThiFormThem(Model model) {
		model.addAttribute("hoaDon", new HoaDon());
		model.addAttribute("danhSachPhong", phongTroService.getAllPhongTro());
		return "views/hoadon/them-hoadon";
	}

	@PostMapping("/them")
	public String themHoaDon(@ModelAttribute HoaDon hoaDon) {
		hoaDonService.taoHoaDon(hoaDon);
		return "redirect:/hoadon/danhsach";
	}

	@PostMapping("/sua")
	public String suaHoaDon(@ModelAttribute HoaDon hoaDon) {
		hoaDonService.capNhatHoaDon(hoaDon);
		return "redirect:/hoadon/danhsach";
	}

	@GetMapping("/thanhtoan/{id}")
	public String thanhToanHoaDon(@PathVariable int id) {
		hoaDonService.thanhToanHoaDon(id);
		return "redirect:/hoadon/danhsach";
	}
}
