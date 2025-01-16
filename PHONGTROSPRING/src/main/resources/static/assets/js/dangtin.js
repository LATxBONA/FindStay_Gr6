
document.addEventListener('DOMContentLoaded', function() {
    const roomTypeSelect = document.getElementById("roomTypeid");
    const addRoomBtnContainer = document.getElementById('addRoomBtnContainer');
    const addRoomButton = document.getElementById('addRoomButton');
    const additionalForms = document.getElementById('additionalForms');
    let formCount = 0;

    roomTypeSelect.addEventListener('change', function() {
        const selectedOption = roomTypeSelect.options[roomTypeSelect.selectedIndex];
        if (selectedOption.text.trim() === "Phòng trọ") {
            addRoomBtnContainer.style.display = 'block';
        } else {
            addRoomBtnContainer.style.display = 'none';
            additionalForms.innerHTML = '';
            formCount = 0;
        }
    });

    // Xử lý sự kiện thêm phòng mới
    addRoomButton.addEventListener('click', function() {
        formCount++;
        const newFormHTML = `
            <div class="dynamic-form" id="roomForm${formCount}">
                <h3>Phòng Trọ ${formCount}</h3>
                <div class="froup-item">
                    <label for="tenPhong${formCount}">Tên Phòng:</label>
                    <input type="text" id="tenPhong${formCount}" name="phongTros[${formCount-1}].tenPhong" required>
                </div>
                <div class="froup-item">
                    <label for="status${formCount}">Trạng Thái:</label>
                    <input type="hidden" name="phongTros[${formCount-1}].status" value="Trống">
                </div>
                <button type="button" onclick="removeForm(${formCount})" class="btn-remove">Xóa Phòng</button>
            </div>
        `;
        additionalForms.insertAdjacentHTML('beforeend', newFormHTML);
    });

    // Xử lý form submit
    document.querySelector('form').addEventListener('submit', function(e) {
        const roomTypeid = roomTypeSelect.value;
        // Nếu không phải loại phòng trọ hoặc không có form phòng nào thì submit bình thường
        if (roomTypeSelect.options[roomTypeSelect.selectedIndex].text.trim() !== "Phòng trọ" || formCount === 0) {
            return true;
        }
    });
});

function removeForm(id) {
    const formToRemove = document.getElementById(`roomForm${id}`);
    if (formToRemove) {
        formToRemove.remove();
        // Cập nhật lại các index trong name của các form còn lại
        const remainingForms = document.querySelectorAll('.dynamic-form');
        remainingForms.forEach((form, index) => {
            const inputs = form.querySelectorAll('input[name^="phongTros"]');
            inputs.forEach(input => {
                const fieldName = input.name.split('.')[1];
                input.name = `phongTros[${index}].${fieldName}`;
            });
        });
    }
}
function loadDistricts() {
    const cityId = document.getElementById("citySelect").value;
    const locationSelect = document.getElementById("districtSelect");
    const districtId = document.getElementById("districtSelect").value;
    const wardSelect = document.getElementById("locationward");
    
    
    
    locationSelect.innerHTML = '<option value="">-- Chọn quận/huyện --</option>'; // Xóa các tùy chọn cũ
    if (cityId) {
        fetch(`/dangtin/${cityId}`) // Gửi GET request
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json(); // Parse JSON
            })
            .then(data => {                
                data.forEach(district => {
                    const option = document.createElement("option");
                    option.value = district.district_id; // Đặt value là districtId
                    option.text = district.district; // Hiển thị tên quận/huyện
                    locationSelect.appendChild(option);
                });
            })
            .catch(error => console.error("Error:", error));
    } else {
        locationSelect.innerHTML = '<option value="">-- Chọn quận/huyện --</option>'; // Reset nếu không chọn thành phố
    }
}

function loadWard() {
    
	const districtId = document.getElementById("districtSelect").value;
    const wardSelect = document.getElementById("locationward");
    //
    wardSelect.innerHTML='<option value="">-- Chọn Phường/Xã --</option>'
    if (districtId) {
        fetch(`/dangtin/city/${districtId}`) // Gửi GET request
            .then(response => {
                if (!response.ok) {
                	
                	console.log(response.body)
                    //throw new Error("Network response was not ok");
                    
                }
                return response.json(); // Parse JSON
            })
            .then(data => {                
                data.forEach(wards => {
                    const options = document.createElement("option");
                    options.value = wards.ward_id; // Đặt value là districtId
                    options.text = wards.ward; // Hiển thị tên quận/huyện
                    wardSelect.appendChild(options);
                });
            })
            .catch(error => console.error("Error:", error));
    } else {
    	wardSelect.innerHTML = '<option value="">-- Chọn Phường/Xã --</option>'; // Reset nếu không chọn thành phố
    }
}
	
function loadTime() {
    const goithoigian = document.getElementById("goitime").value;
    const soNgay = document.getElementById("songay");
    soNgay.innerHTML='<option value="">-- Chọn thời gian --</option>'
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
                    optionss.text = time.time +" "+time.bientime; // Hiển thị tên quận/huyện
                    soNgay.appendChild(optionss);
                });
            })
            .catch(error => console.error("Error:", error));
    } else {
    	soNgay.innerHTML = '<option value="">-- Chọn thời gian --</option>'; // Reset nếu không chọn thành phố
    }
}
