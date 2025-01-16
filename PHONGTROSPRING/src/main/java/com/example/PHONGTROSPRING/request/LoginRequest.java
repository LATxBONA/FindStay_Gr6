package com.example.PHONGTROSPRING.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {

	@Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải là 10 chữ số")
	@NotBlank(message = "Số điện thoại không được để trống")
	private String phoneNumber;

	@Size(min = 8, max = 20, message = "Mật khẩu phải trên 8 kí tự")
	@NotBlank(message = "Mật Khẩu không được để trống")
	private String password;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
