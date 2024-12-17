package com.example.PHONGTROSPRING.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.service.ListingsService;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {


//	@Autowired
//	private ListingsService listingsService;


	@GetMapping({ "", "/" })
	public String dangtin(Model model) {
		// model.addAttribute("list", listingsService.getAllListings());
		return "views/home";
	}
	@GetMapping("banggiadichvu")
	public String banggiadichvu() {
		return "views/footer-infor-about-website/danhchokhachhang/banggiadichvu";
	}

}
