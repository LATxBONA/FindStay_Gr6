document.addEventListener('DOMContentLoaded', function() {
	// Các biến lưu trữ lựa chọn của người dùng
	let selectedRoomCurrent = 0;
	let selectedPriceCurrent = '';
	let selectedAreaCurrent = '';

	// Tọa độ trung tâm thành phố Quy Nhơn (13.782, 109.1907)
	var quyNhonLatLng = [13.782, 109.1907];

	// Khởi tạo bản đồ và cài đặt các tham số ban đầu
	var map = L.map('map').setView(quyNhonLatLng, 13); // Thiết lập vị trí bản đồ và mức zoom ban đầu

	// Thêm tile layer OpenStreetMap vào bản đồ
	L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
		attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
	}).addTo(map); // Thêm bản đồ OpenStreetMap vào trang

	// Thêm marker mặc định tại trung tâm thành phố Quy Nhơn
	var defaultMarker = L.circleMarker(quyNhonLatLng, {
		color: 'red',
		fillColor: 'red',
		fillOpacity: 0.7,
		radius: 10
	}).addTo(map)
		.bindPopup("Trung tâm thành phố Quy Nhơn")
		.openPopup(); // Tạo marker và gắn cửa sổ popup với thông tin

	// Hàm để lấy giá trị của các checkbox đã chọn (các tính năng)
	function getSelectedFeatures() {
		const selectedCheckboxes = document.querySelectorAll('input[name="feature"]:checked'); // Lấy tất cả checkbox đã chọn
		const selectedFeatures = [];
		selectedCheckboxes.forEach(function(checkbox) {
			selectedFeatures.push(checkbox.value); // Lưu giá trị của checkbox vào mảng
		});
		return selectedFeatures;
	}

	// Lắng nghe sự kiện change trên các radio button (loại phòng)
	const radios = document.querySelectorAll('input[name="room"]');
	radios.forEach(function(radio) {
		radio.addEventListener('change', function() {
			const selectedRoom = document.querySelector('input[name="room"]:checked'); // Lấy loại phòng đã chọn
			selectedRoomCurrent = selectedRoom ? selectedRoom.value : 0; // Lưu giá trị phòng đã chọn
			search(selectedRoomCurrent, getSelectedFeatures(), selectedPriceCurrent, selectedAreaCurrent); // Gọi hàm tìm kiếm
		});
	});

	// Lắng nghe sự kiện change trên các radio button (mức giá)
	const priceRadios = document.querySelectorAll('input[name="price"]');
	priceRadios.forEach(function(radio) {
		radio.addEventListener('change', function() {
			const selectedPrice = document.querySelector('input[name="price"]:checked'); // Lấy mức giá đã chọn
			selectedPriceCurrent = selectedPrice ? selectedPrice.value : ''; // Lưu giá trị mức giá
			search(selectedRoomCurrent, getSelectedFeatures(), selectedPriceCurrent, selectedAreaCurrent); // Gọi hàm tìm kiếm
		});
	});

	// Lắng nghe sự kiện change trên các radio button (diện tích)
	const areaRadios = document.querySelectorAll('input[name="area"]');
	areaRadios.forEach(function(radio) {
		radio.addEventListener('change', function() {
			const selectedArea = document.querySelector('input[name="area"]:checked'); // Lấy diện tích đã chọn
			selectedAreaCurrent = selectedArea ? selectedArea.value : ''; // Lưu diện tích đã chọn
			search(selectedRoomCurrent, getSelectedFeatures(), selectedPriceCurrent, selectedAreaCurrent); // Gọi hàm tìm kiếm
		});
	});

	// Lắng nghe sự kiện change trên các checkbox (tính năng)
	const checkboxes = document.querySelectorAll('input[name="feature"]');
	checkboxes.forEach(function(checkbox) {
		checkbox.addEventListener('change', function() {
			const selectedFeatures = getSelectedFeatures(); // Lấy các tính năng đã chọn
			search(selectedRoomCurrent, selectedFeatures, selectedPriceCurrent, selectedAreaCurrent); // Gọi hàm tìm kiếm
		});
	});

	// Khởi động tìm kiếm với các tham số ban đầu
	search(0, getSelectedFeatures(), '', '');

	// Hàm tìm kiếm gửi yêu cầu đến API và cập nhật bản đồ
	function search(roomId, selectedFeatures, price, area) {
		// Xóa tất cả markers hiện tại trên bản đồ
		map.eachLayer((layer) => {
			if (layer instanceof L.Marker) {
				map.removeLayer(layer); // Loại bỏ các marker cũ
			}
		});

		// Gửi yêu cầu AJAX để tìm kiếm
		$.ajax({
			url: 'http://localhost:8080/search_map', // API tìm kiếm
			method: 'GET',
			data: {
				room: roomId,
				features: selectedFeatures,
				price: price,
				area: area
			},
			success: function(res) {
				console.log('Search results:', res); // In ra kết quả tìm kiếm

				// Duyệt qua các kết quả tìm kiếm và tạo marker cho mỗi kết quả
				res.forEach(function(item) {
					// Tạo địa chỉ đầy đủ cho mỗi kết quả tìm kiếm
					var fullAddress = item.address + ', ' + item.location_ward.ward + ', ' + item.location_district.district + ', ' + item.location_city.city;

					// Gọi hàm searchAddressAndAddMarker để lấy tọa độ và thêm marker vào bản đồ
					searchAddressAndAddMarker(fullAddress, item.formattedPrice, item.roomTypeName, item.itemId);
				});
			},
			error: function(xhr, error, status) {
				console.error('Search API error:', error); // In lỗi nếu có
			}
		});
	}

	// Hàm để tìm tọa độ từ địa chỉ và thêm marker vào bản đồ
	async function searchAddressAndAddMarker(address, formattedPrice, roomTypeName, id) {
		if (!address) return; // Kiểm tra nếu không có địa chỉ

		const encodedAddress = encodeURIComponent(address); // Mã hóa địa chỉ

		try {
			const response = await fetch(
				`https://photon.komoot.io/api/?q=${encodedAddress}&limit=1`, // Gửi yêu cầu lấy tọa độ từ API Photon
				{
					method: 'GET',
					headers: {
						'Accept': 'application/json'
					}
				}
			);

			// Kiểm tra nếu phản hồi trả về thành công
			if (response.ok) {
				const data = await response.json(); // Chuyển phản hồi thành JSON
				if (data.features && data.features.length > 0) {
					const coordinates = data.features[0].geometry.coordinates; // Lấy tọa độ từ dữ liệu

					const lat = coordinates[1]; // Vĩ độ
					const lon = coordinates[0]; // Kinh độ

					// Xóa các marker cũ nếu có
					map.eachLayer((layer) => {
						if (layer instanceof L.Marker) {
							const popup = layer.getPopup();
							if (popup && popup.getContent().includes(address)) {
								map.removeLayer(layer);
							}
						}
					});

					// Thêm marker mới vào bản đồ
					const marker = L.marker([lat, lon]).addTo(map);

					// Tạo nội dung cho popup của marker
					const popupContent = `
					    <div class="popup-content">
					        <b>Địa chỉ:</b> ${address}<br>
					        <b>Giá:</b> ${formattedPrice}<br>
					        <b>Loại phòng:</b> ${roomTypeName}
					    </div>
					`;

					// Khi hover qua marker, hiển thị popup
					marker.on('mouseover', function() {
						marker.bindPopup(popupContent).openPopup(); // Hiển thị popup khi hover
					});

					// Khi di chuột ra khỏi marker, ẩn popup
					marker.on('mouseout', function() {
						marker.closePopup(); // Ẩn popup khi di chuột ra
					});

					// Khi click vào marker, mở trang chi tiết phòng
					marker.on('click', function() {
						window.location.href = 'http://localhost:8080/detailRoom/id=' + id; // Chuyển hướng đến trang chi tiết phòng
					});

					return true; // Thành công
				}
			}
		} catch (error) {
			console.error('Geocoding error:', error); // In lỗi nếu có
		}

		return false; // Thất bại
	}

	// Xử lý sự kiện thay đổi lớp bản đồ (Street Layer và Satellite Layer)
	const streetLayer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
		attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
	});

	const satelliteLayer = L.tileLayer('https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}', {
		attribution: '&copy; <a href="https://www.esri.com/en-us/arcgis/about-arcgis/overview">Esri</a> contributors',
		maxZoom: 19
	});

	let currentTileLayer = streetLayer.addTo(map); // Thêm lớp bản đồ mặc định

	// Lắng nghe sự kiện click trên các nút để thay đổi lớp bản đồ
	const btn_change_layer = document.querySelectorAll('.btn_change_layer');
	btn_change_layer.forEach(function(item) {
		item.addEventListener('click', function() {
			map.removeLayer(currentTileLayer); // Xóa lớp bản đồ hiện tại
			if (item.classList.contains('default')) {
				currentTileLayer = streetLayer; // Chuyển sang lớp OpenStreetMap
				item.style.display = 'none'; // Ẩn nút
				btn_change_layer[1].style.display = 'block'; // Hiển thị nút chuyển sang lớp vệ tinh
			} else {
				currentTileLayer = satelliteLayer; // Chuyển sang lớp vệ tinh
				item.style.display = 'none'; // Ẩn nút
				btn_change_layer[0].style.display = 'block'; // Hiển thị nút chuyển sang lớp bản đồ thường
			}
			map.addLayer(currentTileLayer); // Thêm lớp bản đồ mới
		});
	});
});
