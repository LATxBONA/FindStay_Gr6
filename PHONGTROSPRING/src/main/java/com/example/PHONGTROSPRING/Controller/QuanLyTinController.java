package com.example.PHONGTROSPRING.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.ListingsFeatures;
import com.example.PHONGTROSPRING.entities.LocationsCity;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.request.RequestPostNew;
import com.example.PHONGTROSPRING.request.RequestThanhToan;
import com.example.PHONGTROSPRING.service.ListingsService;
import com.example.PHONGTROSPRING.service.LocationsCityService;
import com.example.PHONGTROSPRING.service.QuanLyTinService;
import com.example.PHONGTROSPRING.service.RoomTypesService;
import com.example.PHONGTROSPRING.service.UtitilyService;

import jakarta.servlet.http.HttpSession;

@Controller
public class QuanLyTinController {

	@Autowired
	private ListingsService ListingsService;

	@Autowired
	private LocationsCityService locationsCityService;

	@Autowired
	private RoomTypesService RoomTypesService;

	@Autowired
	private QuanLyTinService quanLyTinService;

	@GetMapping("/quanlytin")
	public String quanlytin(@RequestParam(defaultValue = "0") int page,
			@RequestParam(value = "valuesearch", required = false, defaultValue = "") String valuesearch,
			@RequestParam(value = "valueloaitin", required = false, defaultValue = "999999") int valueloaitin,
			@RequestParam(value = "valuetrangthai", required = false, defaultValue = "") String valuetrangthai,
			Model model, HttpSession session) {

		User user = (User) session.getAttribute("user");
		Pageable pageable = PageRequest.of(page, 10);

		// Xử lý khi không có giá trị tìm kiếm
		Page<Listings> listings;
		if (valuesearch.isEmpty() && valueloaitin == 999999 && valuetrangthai.isEmpty()) {
			listings = ListingsService.getListingByUser(user, pageable);
		} else {
			listings = ListingsService.searchTin(valuetrangthai, valueloaitin, valuesearch, user, pageable);
		}

		model.addAttribute("listtingsss", listings);
		model.addAttribute("valuesearch", valuesearch);
		model.addAttribute("valueloaitin", valueloaitin);
		model.addAttribute("valuetrangthai", valuetrangthai);

		return "views/quanlytin"; // Trả về view quanlytin.html
	}

	@GetMapping("/quanlytin/hide/{id}")
	public String antin(RedirectAttributes redirectattributes,@PathVariable int id) {
		ListingsService.antin(id);
		redirectattributes.addFlashAttribute("successMessage", "Đã ẩn tin số "+ id);

		return "redirect:/quanlytin";
	}

	@GetMapping("/quanlytin/danglai/{id}")
	public String danglai(RedirectAttributes redirectattributes,HttpSession session, @PathVariable int id) {
		User user = (User) session.getAttribute("user");
		
		if(ListingsService.danglai(user,id)) {
			redirectattributes.addFlashAttribute("successMessage", "Đăng lại thành công tin số "+ id);
		}
		else {
			redirectattributes.addFlashAttribute("errorMessage", "Tin đã hết hạn không thể đăng lại");
		}
		return "redirect:/quanlytin";
	}

	@GetMapping("quanlytin/update/{id}")
	public String update(RedirectAttributes redirectattributes ,Model model, @PathVariable int id) {
		Listings listings = ListingsService.findById(id);
		if(listings.getExpiryDate().isBefore(LocalDateTime.now())) {
			model.addAttribute("listings", listings);
			model.addAttribute("listingsfeatures", ListingsService.getListingsFeatures(listings));
			model.addAttribute("locationscity", locationsCityService.getAllCity());
			model.addAttribute("roomtypes", RoomTypesService.getAllRoomTypes());
			return "views/updatetin";
		}
		else {
			redirectattributes.addFlashAttribute("errorMessage", "Gia hạn thất bại");
			return "redirect:/quanlytin";
		}	
	}
	@PostMapping("/quanlytin/update/{id}")
	public String updateExcute(@ModelAttribute RequestPostNew request,
			@PathVariable int id,
			@ModelAttribute ListingsFeatures listingsfeatures) {
		quanLyTinService.updateTin(id,request, listingsfeatures);
		return "redirect:/quanlytin";
	}
	@GetMapping("/quanlytin/giahan/{id}")
	public String giahanExcute(RedirectAttributes redirectattributes,HttpSession session,@ModelAttribute RequestThanhToan rq,@PathVariable int id) {
		User user = (User) session.getAttribute("user");
		if(	quanLyTinService.giahantin(rq, id, user)) {
			redirectattributes.addFlashAttribute("successMessage", "Gia hạn thành công");
		}
		else {
			redirectattributes.addFlashAttribute("errorMessage", "Gia hạn thất bại");
		}
		return "redirect:/quanlytin";
	}
}
