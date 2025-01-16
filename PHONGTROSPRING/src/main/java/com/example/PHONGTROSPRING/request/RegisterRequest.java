package com.example.PHONGTROSPRING.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
	@Size(min = 8, max = 20, message = "mật khẩu phải trên 8 kí tự")
	@NotBlank(message = "Mật khẩu không được để trống")
	private String password;
	
	@Size(min = 8, max = 20, message = "mật khẩu phải trên 8 kí tự")
	@NotBlank(message = "Mật khẩu không được để trống")
	private String confirmPass;
	
	@NotBlank(message = "Họ tên không được để trống")
	private String fullName;
	
	@NotBlank(message = "Số điện thoại không được để trống")
	private String phoneNumber;
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
