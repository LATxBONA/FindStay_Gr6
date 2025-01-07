package com.example.PHONGTROSPRING.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.PHONGTROSPRING.response.ListingsResponse;
import com.example.PHONGTROSPRING.service.ListingsService;

@Controller
public class HomeController {
	@Autowired
	   private ListingsService listingsService;

	@GetMapping("/")
	public String home(
	    @RequestParam(defaultValue = "mac-dinh") String orderby,
	    @RequestParam(defaultValue = "1") int page,  // Đổi giá trị mặc định thành 1
	    Model model) {

	    final int PAGE_SIZE = 2;
	    // Chuyển đổi sang đánh số trang bắt đầu từ 0 cho Spring Data JPA
	    int pageIndex = page - 1;
	    
	    Page<ListingsResponse> listingPage;
	    if (orderby.equals("moi-dang")) {
	        listingPage = listingsService.getAllListingsByNewest(pageIndex, PAGE_SIZE);
	    } else {
	        listingPage = listingsService.getAllListingsApproved(pageIndex, PAGE_SIZE);
	    }

	    List<ListingsResponse> listings = listingsService.setImageForListingsResponse(listingPage);

	    model.addAttribute("list_room", listings);
	    model.addAttribute("totalPage", listingPage.getTotalPages());
	    model.addAttribute("page", page);  // Giữ số trang bắt đầu từ 1 cho view
	    model.addAttribute("orderby", orderby);
	    model.addAttribute("totalPosts", listingPage.getTotalElements());

	    // Điều chỉnh tính toán phân trang cho đánh số bắt đầu từ 1
	    model.addAttribute("prePage", Math.max(1, page - 1));
	    model.addAttribute("nextPage", 
	        page < listingPage.getTotalPages() ? page + 1 : page);

	    return "views/home";
	}
	
	
	
	@GetMapping("banggiadichvu")
	public String banggiadichvu() {
		return "views/footer-infor-about-website/danhchokhachhang/banggiadichvu";
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
