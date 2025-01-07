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
    public String danhSachPhongTro(
            @PathVariable String typeroom, 
            @PathVariable String city,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) Integer district_id,
            @RequestParam(defaultValue = "mac-dinh") String orderby,
            @RequestParam(defaultValue = "1") int page,
            Model model,
            HttpSession session) {

        // Giữ lại giá trị gốc cho URL
        String typeroom_default = typeroom;
        String city_default = city;
        model.addAttribute("city_default", city_default);
        model.addAttribute("typeroom_default", typeroom_default);

        // Chuyển đổi tên hiển thị
        typeroom = convertRoomType(typeroom);
        city = convertCityName(city);

        // Thiết lập tiêu đề và thông tin cơ bản
        model.addAttribute("title", "Cho thuê " + typeroom + " " + city);
        model.addAttribute("city", city);
        model.addAttribute("typeroom", typeroom);
        model.addAttribute("quantity_post", listingsService.getQuantityPost());

        // Lấy roomtype_id
        int roomtype_id = roomTypesService.findRoomTypeByName(typeroom);

        // Xử lý page
        int currentPage = page > 0 ? page - 1 : 0;

        // Xử lý favorite nếu user đã login
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("list_favorite", favoriteService.findByUserId(user.getUserId()));
        }

        final int PAGE_SIZE = 3;
        Page<ListingsResponse> listing;

        // Xử lý tìm kiếm theo các trường hợp
        if (city.equals("Toàn quốc")) {
            if (orderby.equals("moi-dang")) {
                listing = listingsService.getListingsByNewest(roomtype_id, currentPage, PAGE_SIZE);
            } else {
                listing = listingsService.getListingsNationWide(roomtype_id, currentPage, PAGE_SIZE);
            }
        } else {
            int city_id = locationsCityService.findByCityName(city);
            model.addAttribute("list_district", 
                UtitilyService.changeDistrictName(locationsDistrictService.getDistrict(city_id)));

            if (district_id != null) {
                listing = listingsService.getListingsByDistrictAndOrder(
                    roomtype_id, 
                    city_id,
                    district_id,
                    orderby,
                    currentPage, 
                    PAGE_SIZE
                );
                model.addAttribute("current_district", district);
                model.addAttribute("current_district_id", district_id);
            } else {
                if (orderby.equals("moi-dang")) {
                    listing = listingsService.getListingsByNewestAndCity(roomtype_id, city_id, currentPage, PAGE_SIZE);
                } else {
                    listing = listingsService.getListings(roomtype_id, city_id, currentPage, PAGE_SIZE);
                }
            }
        }

        // Xử lý phân trang
        int totalPages = listing.getTotalPages();
        model.addAttribute("list_room", setImageForListingsResponse(listing));
        model.addAttribute("totalPage", totalPages);
        model.addAttribute("page", currentPage);
        model.addAttribute("orderby", orderby);
        
        // Tính toán các trang cho phân trang
        int prePage = Math.max(0, currentPage - 1);
        int nextPage = Math.min(totalPages - 1, currentPage + 1);
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
