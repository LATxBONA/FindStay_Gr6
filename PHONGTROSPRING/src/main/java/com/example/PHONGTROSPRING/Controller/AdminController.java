package com.example.PHONGTROSPRING.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.Transactions;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.request.AccountRequest;
import com.example.PHONGTROSPRING.request.TransactionsRequest;
import com.example.PHONGTROSPRING.service.AdminService;
import com.example.PHONGTROSPRING.service.ListingsService;
import com.example.PHONGTROSPRING.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService userService;

	@Autowired
	private ListingsService listingServive;

	@GetMapping("/")
	public String viewAdmin() {
		return "views/admin/layout/layoutAdmin";
	}

	// transactions-------------------------------
	@GetMapping("/transactions")
	public String viewTransactions(HttpSession session, Model model) {

		List<Transactions> transactions = adminService.viewTransactions();
		model.addAttribute("transactions", transactions);
		model.addAttribute("contentTemplate", "views/admin/transactions");
		return "views/admin/layout/layoutAdmin";
	}

	@PostMapping("/transactions/update")
	public String updateTransactions(HttpSession session, Integer transactionId, String newStatus, Model model) {
		// Kiểm tra xem người dùng đã đăng nhập chưa
		// if (session.getAttribute("user") == null) {
		// return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu chưa đăng
		// nhập
		// }

		adminService.updateTransactions(transactionId, newStatus);
		return "redirect:/admin/transactions";
	}

	@GetMapping("/transactions/search")
	public String filterTransactions(@ModelAttribute TransactionsRequest transactionsRequest, HttpSession session,
			Model model) {

		// Kiểm tra xem người dùng đã đăng nhập chưa
		// if (session.getAttribute("user") == null) {
		// return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu chưa đăng
		// nhập
		// }

		List<Transactions> transactions = adminService.searchTransactions(transactionsRequest);

		model.addAttribute("transactions", transactions);
		model.addAttribute("transactionsRequest", transactionsRequest);
		model.addAttribute("contentTemplate", "views/admin/transactions");

		return "views/admin/layout/layoutAdmin";
	}

	// Account-------------------------
	@GetMapping("/accounts")
	public String viewUsers(Model model) {

		List<User> users = userService.getAllUser();
		model.addAttribute("users", users);
		model.addAttribute("contentTemplate", "views/admin/accounts");
		return "views/admin/layout/layoutAdmin";
	}

	@GetMapping("/accounts/search")
	public String filterAccount(@ModelAttribute AccountRequest accountRequest, Model model) {
		List<User> accounts = userService.filterUser(accountRequest);
		model.addAttribute("users", accounts);
		model.addAttribute("accountRequest", accountRequest);
		model.addAttribute("contentTemplate", "views/admin/accounts");
		return "views/admin/layout/layoutAdmin";
	}

	@GetMapping("accounts/new")
	public String showCreateForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("contentTemplate", "views/admin/createAccount");
		return "views/admin/layout/layoutAdmin";
	}

	@PostMapping("accounts/save")
	public String saveAccount(@ModelAttribute AccountRequest user) {
		userService.save(user);
		return "redirect:/admin/accounts";
	}

	@GetMapping("/accounts/edit/{id}")
	public String showEditForm(@PathVariable String id, Model model) {
		Optional<User> user = userService.findById(id);
		model.addAttribute("user", user.get());
		model.addAttribute("contentTemplate", "views/admin/createAccount");
		return "views/admin/layout/layoutAdmin";
	}

	@PostMapping("/accounts/delete/{id}")
	public String deleteAccount(@PathVariable String id) {
		userService.deleteById(id);
		return "redirect:/admin/accounts";
	}

	// listings----------------------------------------------
	@GetMapping("listings")
	public String viewListing(Model model) {
		List<Listings> listings = listingServive.getAllListings();
		model.addAttribute("listings", listings);
		model.addAttribute("contentTemplate", "views/admin/listings");

		return "views/admin/layout/layoutAdmin";
	}

	@GetMapping("listings/search")
	public String filterListing(String status, Model model) {
		List<Listings> listings = listingServive.getListingByStatus(status);

		model.addAttribute("listings", listings);
		model.addAttribute("contentTemplate", "views/admin/listings");
		return "views/admin/layout/layoutAdmin";
	}

	@PostMapping("listings/update")
	public String updateListing(Integer itemId, String newStatus, Model model) {

		listingServive.updateListing(itemId, newStatus);

		return "redirect:/admin/listings";

	}
}
