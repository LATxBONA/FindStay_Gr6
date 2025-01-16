package com.example.PHONGTROSPRING.Controller;

import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.service.PaymentHistoryService;

import jakarta.servlet.http.HttpSession;


@Controller
public class PayHistoryController {

	@Autowired
	private PaymentHistoryService paymentHistoryService;
	
	@GetMapping("/pay")
	public String Payment(Model model, HttpSession session, @RequestParam(defaultValue = "0") int page) {
			
		User user = (User) session.getAttribute("user");
		Pageable pageable = PageRequest.of(page, 10);
		
		model.addAttribute("payments", paymentHistoryService.getPaymentHistory(user, pageable));
		return "views/PayHistory";	
	}

	
	
}
