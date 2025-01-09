package com.example.PHONGTROSPRING.config;

import com.example.PHONGTROSPRING.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {
	@Override
	// Chạy trước khi request đến controller
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// Kiểm tra nếu không phải admin thì redirect về trang chủ
		if (user == null || !user.getRole().equals("Quản trị viên")) {
			response.sendRedirect("/");
			return false; // Chặn request nếu không phải admin
		}
		return true; // Cho phép request đi tiếp nếu là admin
	}
}
