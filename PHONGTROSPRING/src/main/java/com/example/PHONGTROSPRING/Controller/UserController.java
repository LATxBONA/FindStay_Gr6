package com.example.PHONGTROSPRING.Controller;

import java.lang.ProcessBuilder.Redirect;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.request.LoginRequest;
import com.example.PHONGTROSPRING.request.RegisterRequest;
import com.example.PHONGTROSPRING.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String loginPage(Model model) {
		if (model.containsAttribute("registerSuccess")) {
			model.addAttribute("loginMessage", model.getAttribute("registerSuccess"));
		}
		model.addAttribute("userlogin", new LoginRequest());
		return "views/login";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("userlogin") LoginRequest user, BindingResult result, Model model,
			HttpSession session) {
		if (result.hasErrors()) {
			return "views/login";
		}
		if (userService.login(user) != null) {
			model.addAttribute("login", "Đăng nhập thành công");
			session.setAttribute("user", userService.login(user));
			if (userService.login(user).getRole().equals("Quản trị viên")) {
	            return "redirect:/admin/accounts";
	        }
			return "redirect:/";

		}
		model.addAttribute("loginfail", "Tài khoản hoặc mật khẩu không chính xác");
		return "views/login";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("userRegister", new RegisterRequest());
		return "views/register";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("userRegister") RegisterRequest user, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "views/register";
		}
		if (!user.getPassword().equals(user.getConfirmPass())) {
			model.addAttribute("erros", "Mật khẩu không trùng khớp");
			return "views/register";
		}
		if (userService.checkPhone(user.getPhoneNumber())) {
			model.addAttribute("erros", "Tài khoản đã tồn tại trên hệ thống");
			return "views/register";
		}
		if (userService.register(user)) {
			redirectAttributes.addFlashAttribute("registerSuccess", "Đăng kí thành công , vui lòng đăng nhập");
			return "redirect:/login";
		} else {
			model.addAttribute("erros", "Có lỗi xảy ra");
			return "views/register";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		// Hủy session
		request.getSession().invalidate();
		return "redirect:/";
	}

	@GetMapping("/info")
	public String info(Model model , HttpSession session) {
			User u = (User) session.getAttribute("user");
			if(u!= null) {
				User userMain = userService.getUser(u.getUserId());
				model.addAttribute("infoUser", userMain);
				return "views/info";
			}
		return "redirect:/login";
	}
	
	@PostMapping("/changeInfo")
	public String changeInfo(RedirectAttributes redirectAttributes,@RequestParam String fullName , @RequestParam String email , HttpSession session ,Model model ) {
		User u = (User) session.getAttribute("user");
		u.setEmail(email);
		u.setFullName(fullName);
		if(userService.updateUser(u)) {
			User update = userService.getUser(u.getUserId());
			redirectAttributes.addFlashAttribute("status", "Cập nhật thành công");
//			model.addAttribute("status", "Cập nhật thành công");
			session.setAttribute("user", update);
			return "redirect:/info";
		}
		model.addAttribute("status", "Có lỗi xảy ra");
		return "redirect:/info";
		
	}
	@PostMapping("/changePass")
	public String changPass(RedirectAttributes redirectAttributes,@RequestParam String currentPassword ,@RequestParam String newPassword , HttpSession session , Model model) {
		User u = (User) session.getAttribute("user");
		if(userService.changePass(u,currentPassword,newPassword)) {
			redirectAttributes.addFlashAttribute("status", "Đổi mật khẩu thành công");
			return "redirect:/info";
		}
		redirectAttributes.addFlashAttribute("status", "Đổi mật khẩu thất bại");
		return "redirect:/info";	
	}
}
