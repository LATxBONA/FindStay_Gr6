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
	   public String home(@RequestParam(defaultValue = "mac-dinh") String orderby,
	                     @RequestParam(defaultValue = "0") int page,
	                     Model model) {

	       Page<ListingsResponse> listingPage;
	       if(orderby.equals("moi-dang")) {
	           listingPage = listingsService.getAllListingsByNewest(page, 10); 
	       } else {
	           listingPage = listingsService.getAllListingsApproved(page, 10);
	       }

	       List<ListingsResponse> listings = listingsService.setImageForListingsResponse(listingPage);

	       model.addAttribute("list_room", listings);
	       model.addAttribute("totalPage", listingPage.getTotalPages());
	       model.addAttribute("page", page);
	       model.addAttribute("orderby", orderby);
	       model.addAttribute("totalPosts", listingPage.getTotalElements());
	       // Phân trang 
	       if (page > 0) {
	           model.addAttribute("prePage", page - 1);
	       } else {
	           model.addAttribute("prePage", 0);
	       }
	       
	       if (page < listingPage.getTotalPages() - 1) {
	           model.addAttribute("nextPage", page + 1); 
	       } else {
	           model.addAttribute("nextPage", page);
	       }

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
