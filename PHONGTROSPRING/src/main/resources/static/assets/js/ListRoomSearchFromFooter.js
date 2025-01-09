document.addEventListener("DOMContentLoaded", function() {
    var lastSegment = window.location.pathname.split('/').pop();
    var listItems = document.querySelectorAll('.list_menu li');
    var list_district = document.getElementById('list_district');
    var mac_dinh = document.getElementById("mac-dinh");
    var moi_dang = document.getElementById("moi-dang");
    var urlParams = new URLSearchParams(window.location.search);
    var orderby = urlParams.get('orderby');

    // Xử lý hiển thị menu và màu sắc của mục đã chọn
    listItems.forEach(function(item) {
        if (item.classList.contains(lastSegment) && !item.classList.contains('toanquoc')) {
            list_district.style.display = "grid";
            item.classList.add('active_menu');
            item.querySelector('a').style.color = "#D64646";
            listItems[0].classList.remove('active_menu');
            listItems[0].querySelector('a').style.color = "black";
        }

        if (item.classList.contains('toanquoc')) {
            item.classList.add('active_menu');
            item.querySelector('a').style.color = "#D64646";
        }
    });

    // Kiểm tra tham số orderby và thêm class 'active_a'
    if (moi_dang.id === orderby) {
        moi_dang.classList.add('active_a');
    } else if (mac_dinh.id === orderby || orderby === null) {
        mac_dinh.classList.add('active_a');
    }

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
        } else if (button.classList.contains('item_room_favorite_1') || button.classList.contains('item_room_favorite_2')) {
            var itemId = button.getAttribute('itemId');
            $.ajax({
                url: '/add_favorite/' + itemId,
                method: 'GET',
                success: function(response) {
                    if (response == "") {
                        redirectFavoritepage();
                    } else {
                        window.location.reload();
                    }
                },
                error: function(xhr, status, error) {
                    console.error('Error:', error);
                }
            });
        }
    });

    // Hiển thị district name nếu có
    var district = urlParams.get('district');
    var districtNameDiv = document.getElementById('district_name');
	if(districtNameDiv){
		districtNameDiv.innerHTML = district ? ' / ' + district : '';
	}
    

    // Hàm xử lý chuyển hướng đến trang yêu thích
    function redirectFavoritepage() {
        var header_popup = document.getElementById("header_popup");
        var popup = document.getElementById("popup");
        header_popup.classList.add("show_header_popup");
        popup.classList.add("show_popup");

        header_popup.addEventListener("click", function() {
            header_popup.classList.remove("show_header_popup");
            popup.classList.remove("show_popup");
        });

        popup.addEventListener("click", function(e) {
            e.stopPropagation();
        });
    }
});
