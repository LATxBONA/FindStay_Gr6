package com.example.PHONGTROSPRING.Controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PayHistoryController {

	@GetMapping("/pay")
	public String Payment() {

		return "views/PayHistory";
	}

	
	
}
