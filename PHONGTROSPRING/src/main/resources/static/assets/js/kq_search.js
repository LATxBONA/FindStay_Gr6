document.addEventListener("DOMContentLoaded", function() {
    // Xử lý sự kiện cho các nút gọi điện và Zalo
    document.querySelectorAll('.btn-phone, .btn-zalo').forEach(button => {
        button.addEventListener('click', function(event) {
            event.stopPropagation();
            event.preventDefault();

            if (button.classList.contains('btn-phone')) {
                var phoneText = button.textContent;
                window.location.href = "tel:+84" + phoneText.substring(1);
            } else if (button.classList.contains('btn-zalo')) {
                var phone = button.getAttribute('data-phone');
                window.location.href = "http://zalo.me/" + phone;
            }
        });
    });
});
