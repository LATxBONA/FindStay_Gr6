//JavaScript
    // Khởi tạo các biến toàn cục
    let minPrice = null,
        maxPrice = null,
        minArea = null,
        maxArea = null,
        roomType = null,
        city_id = null,
        district_id = null,
        ward_id = null;

    // Modal xử lý
    const priceModal = document.getElementById("priceModal");
    const areaModal = document.getElementById("areaModal");

    

 // Mở modal khi bấm vào input
    document.getElementById("priceInput").onclick = () => priceModal.style.display = "block";
    document.getElementById("closePriceModalButton").onclick = () => priceModal.style.display = "none";

    // Xử lý chọn giá
    document.getElementById("applyPrice").onclick = function () {
        const selected = document.querySelector('input[name="priceRange"]:checked');
        if (selected) {
            [minPrice, maxPrice] = selected.value.split('-').map(Number);
            document.getElementById("priceInput").value = selected.nextSibling.textContent.trim();
        }
        priceModal.style.display = "none";
    };


		    // Xử lý chọn diện tích
		// Mở modal khi bấm vào input
		document.getElementById("areaInput").onclick = () => areaModal.style.display = "block";
		document.getElementById("closeAreaModalButton").onclick = () => areaModal.style.display = "none";
		// Xử lý chọn diện tích
		document.getElementById("applyArea").onclick = function () {
		    const selected = document.querySelector('input[name="areaRange"]:checked');
		    if (selected) {
		        [minArea, maxArea] = selected.value.split('-').map(Number);
		        document.getElementById("areaInput").value = selected.nextSibling.textContent.trim();
		    }
		    areaModal.style.display = "none";
		};


    // Xử lý khi chọn loại phòng
    document.getElementById("roomTypeSelect").onchange = function () {
        roomType = this.value; // Cập nhật loại phòng
    };
    
    //Xử lý modal khu vực
// Mở modal khi bấm vào input
const locationModal = document.getElementById("locationModal");
document.getElementById("locationInput").onclick = () => locationModal.style.display = "block";
document.getElementById("closeLocationModalButton").onclick = () => locationModal.style.display = "none";

    

    // Xử lý khi chọn thành phố
    document.getElementById("CitySelect").onchange = async function () {
        city_id = this.value; // Cập nhật giá trị city_id
        const districtSelect = document.getElementById("DistrictSelect");
        const wardSelect = document.getElementById("WardSelect");

        // Reset các dropdown
        districtSelect.innerHTML = '<option value="" disabled selected>-- Chọn quận/huyện --</option>';
        wardSelect.innerHTML = '<option value="" disabled selected>-- Chọn phường/xã --</option>';
        wardSelect.disabled = true;

        if (city_id) {
            const response = await fetch(`/getDistrictsByCity/${city_id}`);
            const districts = await response.json();

            districts.forEach(district => {
                const option = document.createElement("option");
                option.value = district.district_id;
                option.textContent = district.district;
                districtSelect.appendChild(option);
            });

            districtSelect.disabled = false;
        }
    };

    // Xử lý khi chọn quận/huyện
    document.getElementById("DistrictSelect").onchange = async function () {
        district_id = this.value; // Cập nhật giá trị district_id
        const wardSelect = document.getElementById("WardSelect");

        // Reset danh sách phường/xã
        wardSelect.innerHTML = '<option value="" disabled selected>-- Chọn phường/xã --</option>';

        if (district_id) {
            const response = await fetch(`/getWardsByDistrict/${district_id}`);
            const wards = await response.json();

            wards.forEach(ward => {
                const option = document.createElement("option");
                option.value = ward.ward_id;
                option.textContent = ward.ward;
                wardSelect.appendChild(option);
            });

            wardSelect.disabled = false;
        }
    };

    // Xử lý khi chọn phường/xã
    document.getElementById("WardSelect").onchange = function () {
        ward_id = this.value; // Cập nhật giá trị ward_id
    };
    
 // Áp dụng khu vực
    document.getElementById("applyLocation").onclick = function () {
        const selectedLocationText = [
            city_id ? document.getElementById("CitySelect").selectedOptions[0].text : null,
            district_id ? document.getElementById("DistrictSelect").selectedOptions[0].text : null,
            ward_id ? document.getElementById("WardSelect").selectedOptions[0].text : null
        ].filter(Boolean).join(", ");

        document.getElementById("locationInput").value = selectedLocationText || "Chưa chọn";
        locationModal.style.display = "none";
    };

    // Xử lý nút tìm kiếm
    document.getElementById("searchButton").onclick = function () {
        const url = new URL(window.location.origin + '/search');
        if (minPrice !== null) url.searchParams.set('minPrice', minPrice);
        if (maxPrice !== null) url.searchParams.set('maxPrice', maxPrice);
        if (minArea !== null) url.searchParams.set('minArea', minArea);
        if (maxArea !== null) url.searchParams.set('maxArea', maxArea);
        if (roomType) url.searchParams.set('roomType', roomType);
        if (city_id) url.searchParams.set('city_id', city_id);
        if (district_id) url.searchParams.set('district_id', district_id);
        if (ward_id) url.searchParams.set('ward_id', ward_id);

        console.log("Generated URL:", url.toString()); // Debug URL
        window.location.href = url.toString();
    };
