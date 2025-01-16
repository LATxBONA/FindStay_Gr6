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
	
	// Xử lý sự kiện cho các nút phone và Zalo
	    document.addEventListener('click', function(event) {
	        var button = event.target.closest('.btn-phone, .btn-zalo, .item_room_favorite_1, .item_room_favorite_2');
	        
	        if (!button) return;

	        event.stopPropagation(); // Ngừng sự kiện lan truyền lên thẻ a
	        event.preventDefault();  // Ngừng hành động mặc định của thẻ a

	        // Kiểm tra loại nút được nhấn
	        if (button.classList.contains('btn-phone')) {
	            var phoneText = button.textContent;
	            window.location.href = "tel:+84" + phoneText.substring(1);
	        } else if (button.classList.contains('btn-zalo')) {
	            var phone = button.getAttribute('data-phone');
	            window.location.href = "http://zalo.me/" + phone;
	        }
	    });
})