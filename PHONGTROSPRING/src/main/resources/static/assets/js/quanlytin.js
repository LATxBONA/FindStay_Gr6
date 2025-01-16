function openModal() {
	document.getElementById("exampleModal").style.display = "flex";
}

function closeModal() {
	document.getElementById("exampleModal").style.display = "none";
}

function loadTime() {
	const goithoigian = document.getElementById("goitime").value;
	const soNgay = document.getElementById("songay");
	soNgay.innerHTML = '<option value="">-- Chọn thời gian --</option>'
	if (goithoigian) {
		fetch(`/dataget/${goithoigian}`) // Gửi GET request
			.then(response => {
				if (!response.ok) {
					throw new Error("Network response was not ok");
				}
				return response.json(); // Parse JSON
			})
			.then(data => {
				data.forEach(time => {
					const optionss = document.createElement("option");
					optionss.value = time.time; // Đặt value là districtId
					optionss.text = time.time + " " + time.bientime; // Hiển thị tên quận/huyện
					soNgay.appendChild(optionss);
				});
			})
			.catch(error => console.error("Error:", error));
	} else {
		soNgay.innerHTML = '<option value="">-- Chọn thời gian --</option>'; // Reset nếu không chọn thành phố
	}
}

document.addEventListener('DOMContentLoaded', function() {
	var model_menu = document.getElementById('model_menu');
	var btn_giahan_submit = document.getElementById('btn_giahan_submit');
	var btn_giahans = document.querySelectorAll('.gia_han_tin')
	var btn_close_model = document.getElementById('btn_close_model');


	btn_close_model.addEventListener('click', function() {
		model_menu.classList.remove('show_model');

	});
	btn_giahans.forEach(function(button) {
	    button.addEventListener('click', function() {
	        model_menu.classList.add('show_model');
	        
	        var data = button.getAttribute('data_item');
	        console.log(data);

	        btn_giahan_submit.addEventListener('click', function() {
	            let request = {
	                goitime: document.getElementById('goitime').value,
	                songay: parseInt(document.getElementById('songay').value)
	            };

	            $.ajax({
	                url: '/quanlytin/giahan/' + data,
	                method: 'POST',
	                contentType: 'application/json',
	                data: JSON.stringify(request),
	                success: function(response) {
	                    alert(response.message); // Hiển thị thông báo thành công
	                    location.reload(); // Reload trang nếu cần cập nhật giao diện
	                },
	                error: function(xhr) {
	                    if (xhr.responseJSON && xhr.responseJSON.message) {
	                        alert(xhr.responseJSON.message); // Hiển thị thông báo thất bại
	                    } else {
	                        alert("Cập nhật thất bại. Vui lòng thử lại!");
	                    }
	                }
	            });
	        });
	    });
	});



})