$(document).ready(function() {
	// Khi người dùng nhập số tiền vào ô input
	$(".input-amount").on("input", function() {
		$("input[name='amount']:checked").prop("checked", false); // Bỏ chọn radio
		$(".error-message").remove(); // Xóa thông báo lỗi nếu có
	});
});
