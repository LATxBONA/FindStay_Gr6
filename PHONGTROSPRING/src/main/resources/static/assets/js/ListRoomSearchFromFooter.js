document.addEventListener("DOMContentLoaded", function() {
	var lastSegment = window.location.pathname.split('/').pop();

	var listItems = document.querySelectorAll('.list_menu li');

	listItems.forEach(function(item) {
		if (item.classList.contains(lastSegment)) {
			item.classList.add('active_menu');

			var anchor = item.querySelector('a');
			if (anchor) {
				anchor.style.color = "#D64646";
			}
		}
	});

	var mac_dinh = document.getElementById("mac-dinh");
	var moi_dang = document.getElementById("moi-dang");

	var urlParams = new URLSearchParams(window.location.search);
	var orderby = urlParams.get('orderby');

	if (moi_dang.id == orderby) {
		moi_dang.classList.add('active_a');
	} else if (mac_dinh.id == orderby || orderby == null) {
		mac_dinh.classList.add('active_a');
	}

	// Xử lý sự kiện cho các nút
	document.querySelectorAll('.btn-phone, .btn-zalo').forEach(button => {
		button.addEventListener('click', function(event) {
			event.stopPropagation(); // Ngừng sự kiện lan truyền lên thẻ a
			event.preventDefault();  // Ngừng hành động mặc định của thẻ a

			// Kiểm tra loại nút được nhấn
			if (button.classList.contains('btn-phone')) {
				var phoneText = document.querySelector('.btn-phone').textContent;
				window.location.href = "tel:+84" + phoneText.substring(1);
			} else if (button.classList.contains('btn-zalo')) {
				var phone = button.getAttribute('data-phone');
				window.location.href = "http://zalo.me/" + phone;
			}
		});
	});

	document.querySelectorAll('.item_room_favorite_1, .item_room_favorite_2').forEach(button => {
		button.addEventListener('click', function(event) {
			event.stopPropagation(); // Ngừng sự kiện lan truyền lên thẻ a
			event.preventDefault();  // Ngừng hành động mặc định của thẻ a

			// Kiểm tra loại nút được nhấn
			if (button.classList.contains('btn-phone')) {
				var phoneText = document.querySelector('.btn-phone').textContent;
				window.location.href = "tel:+84" + phoneText.substring(1);
			} else if (button.classList.contains('btn-zalo')) {
				var phone = button.getAttribute('data-phone');
				window.location.href = "http://zalo.me/" + phone;
			}
		});
	});	
});
