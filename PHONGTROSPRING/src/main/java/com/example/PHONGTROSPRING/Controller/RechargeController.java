package com.example.PHONGTROSPRING.Controller;

import com.example.PHONGTROSPRING.entities.Transactions;
import com.example.PHONGTROSPRING.request.RechargeRequest;
import com.example.PHONGTROSPRING.service.RechargeService;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class RechargeController {

	@Autowired
	private RechargeService rechargeService;

	@GetMapping("/recharge")
	public String rechargePage(HttpSession session, Model model) {
		// Kiểm tra xem người dùng đã đăng nhập chưa
		if (session.getAttribute("user") == null) {
			return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
		}

		return "views/recharge"; // Hiển thị form nạp tiền
	}

	@GetMapping("/recharge2")
	public String rechergePage2(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
		}
		model.addAttribute("rechargeRequest", new RechargeRequest());
		return "views/recharge2";
	}



	
	@PostMapping("/recharge3")
	public String recharge(@Valid @ModelAttribute RechargeRequest rechargeRequest, BindingResult result, Model model,
			HttpSession session) {

		if (session.getAttribute("user") == null) {
			return "redirect:/login";  // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
		}

		if (result.hasErrors()) {
			model.addAttribute("rechargefail", "Số tiền không hợp lệ");
			return "views/recharge2";  // Trả về trang nạp tiền kèm lỗi
		}

		// Thành công: truyền số tiền vào model để hiển thị
		BigDecimal amount = rechargeRequest.getAmount();

		model.addAttribute("amount", amount);

		return "views/recharge3"; // Điều hướng sang trang xác nhận
	}

	@GetMapping("/recharge3")
	public String rechargePage3(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			return "redirect:/login";  // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
		}
		model.addAttribute("rechargeRequest", new RechargeRequest());
		return "views/recharge3";
	}

	@GetMapping("/recharge4")
	public String rechargeHistory(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			return "redirect:/login";  // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
		}
		List<Transactions> transaction = rechargeService.getRechargeHistory(session);
		model.addAttribute("transactions", transaction);
		return "views/rechargeHistory";
	}

	@PostMapping("/recharge4")
	public String rechargeConfirm(@Valid @ModelAttribute RechargeRequest rechargeRequest, HttpSession session,
			Model model) {
		rechargeService.createRechargeRequest(rechargeRequest, session);
		return "redirect:/recharge4";
	}


}
