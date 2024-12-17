package com.example.PHONGTROSPRING.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.response.ListingsResponse;
import com.example.PHONGTROSPRING.service.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class FooterController {

	@Autowired
	private ListingsService listingsService;

	@Autowired
	private LocationsCityService locationsCityService;

	@Autowired
	private LocationsDistrictService locationsDistrictService;

	@Autowired
	private RoomTypesService roomTypesService;

	@Autowired
	private FavoritePageService favoriteService;

	@GetMapping("/footer/{page}")
	public String redirectPage(@PathVariable String page) {
		switch (page) {
		case "gioithieu": {
			return "views/footer-infor-about-website/vephongtro/gioithieu";
		}
		case "quychehoatdong": {
			return "views/footer-infor-about-website/vephongtro/quychehoatdong";
		}
		case "quydinhsudung": {
			return "views/footer-infor-about-website/vephongtro/quydinhsudung";
		}
		case "chinhsachbaomat": {
			return "views/footer-infor-about-website/vephongtro/chinhsachbaomat";
		}
		case "cauhoithuonggap": {
			return "views/footer-infor-about-website/danhchokhachhang/cauhoithuonggap";
		}
		case "huongdandangtin": {
			return "views/footer-infor-about-website/danhchokhachhang/huongdandangtin";
		}
		case "quydinhdangtin": {
			return "views/footer-infor-about-website/danhchokhachhang/quydinhdangtin";
		}
		case "banggiadichvu": {
			return "views/footer-infor-about-website/danhchokhachhang/banggiadichvu";
		}
		case "giaiquyetkhieunai": {
			return "views/footer-infor-about-website/danhchokhachhang/giaiquyetkhieunai";
		}
		default:
			return "views/footer-infor-about-website/vephongtro/gioithieu";
		}
	}

	private String convertRoomType(String typeroom) {
		switch (typeroom) {
		case "phongtro":
			return "Phòng trọ";
		case "thuenha":
			return "Nhà nguyên căn";
		case "thuecanho":
			return "Căn hộ";
		case "thuematbang":
			return "Mặt bằng";
		case "oghep":
			return "Ở Ghép";
		default:
			return "Phòng trọ";
		}
	}

	private String convertCityName(String city) {
		switch (city) {
		case "HCM":
			return "Thành phố Hồ Chí Minh";
		case "HN":
			return "Hà Nội";
		case "BD":
			return "Tỉnh Bình Định";
		default:
			return "Toàn quốc";
		}
	}

	@GetMapping("/{typeroom}/{city}")
	public String danhSachPhongTro(@PathVariable String typeroom, @PathVariable String city,
			@RequestParam(value = "orderby", defaultValue = "mac-dinh") String orderby,
			@RequestParam(value = "page", defaultValue = "0") int page, Model model, HttpSession session) {

		String typeroom_default = typeroom;
		String city_default = city;

		model.addAttribute("city_default", city_default);
		model.addAttribute("typeroom_default", typeroom_default);

		typeroom = convertRoomType(typeroom);
		city = convertCityName(city);

		// Tiêu đề page
		model.addAttribute("title", "Cho thuê " + typeroom + city);

		model.addAttribute("city", city);
		model.addAttribute("typeroom", typeroom);

		// Số lượng bài đăng
		model.addAttribute("quantity_post", listingsService.getQuantityPost());

		int roomtype_id = roomTypesService.findRoomTypeByName(typeroom);

		if (page - 1 < 0) {
			page = 0;
		} else {
			page -= 1;
		}

		// favorite heart
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("list_favorite", favoriteService.findByUserId(user.getUserId()));
		}

		int totalPage = 0;
		Page<ListingsResponse> listing = null;

		if (city.equals("Toàn quốc")) {

			listing = listingsService.getListingsNationWide(roomtype_id, page, 15);
			totalPage = listing.getTotalPages();
			
		} else {
			int city_id = locationsCityService.findByCityName(city);

			// District của city tương ứng
			model.addAttribute("list_district",
					UtitilyService.changeDistrictName(locationsDistrictService.getDistrict(city_id)));

			listing = listingsService.getListings(roomtype_id, city_id, page, 15);
			totalPage = listing.getTotalPages();
		}
		
		model.addAttribute("list_room", setImageForListingsResponse(listing));
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);

		int prePage = page - 1;
		int nextPage = page + 1;

		if (page < 1) {
			prePage = 0;
			nextPage = page + 2;
		}

		if (page == totalPage - 1) {
			nextPage = totalPage - 1;
			prePage = page - 2;
		}

		model.addAttribute("prePage", prePage);
		model.addAttribute("nextPage", nextPage);

		return "views/ListRoomsSearchFromFooter";
	}

	// set ảnh cho từng đối tượng listingsResponse
	public List<ListingsResponse> setImageForListingsResponse(Page<ListingsResponse> listingResponse) {
		List<ListingsResponse> list = new ArrayList<>();

		for (ListingsResponse item : listingResponse) {
			if (listingsService.findImageByItemId(item.getItemId()).size() > 0) {
				item.setImageUrl(listingsService.findImageByItemId(item.getItemId()).get(0));
			}
			list.add(item);
		}

		return list;
	}
}
