document.addEventListener("DOMContentLoaded", function() {
	// Lắng nghe sự kiện click trên tất cả các btn_favorite
	document.querySelectorAll('.btn_favorite').forEach(function(btnFavorite) {
		btnFavorite.addEventListener('click', function(event) {
			// Ngừng hành động mặc định của thẻ <a> khi nhấn vào nút yêu thích
			event.stopPropagation();  // Ngừng sự kiện "click" lan rộng đến <a>
			event.preventDefault();   // Ngừng hành động mặc định (chuyển hướng)

			// Lấy thẻ <img> đầu tiên trong btn_favorite
			var firstImage = btnFavorite.querySelector('img'); // Lấy thẻ <img> đầu tiên

			// Lấy giá trị của thuộc tính itemId
			var itemId = firstImage.getAttribute('itemId');

			// Kiểm tra xem thẻ <img> có tồn tại không
			if (firstImage) {
				firstImage.style.display = "none";
			}

			$.ajax({
				url: '/delete_favorite/' + itemId,
				method: 'GET',
				success: function() {
					window.location.reload();
				},
				error: function(xhr, status, error) {
					console.error('Error:', error);
				}
			});
		});
	});
})