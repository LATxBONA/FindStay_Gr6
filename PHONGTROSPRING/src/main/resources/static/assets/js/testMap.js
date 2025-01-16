document.addEventListener("DOMContentLoaded", function() {
    var locations = [
        { address: "68 Nguyễn Thái Học, TP Quy Nhơn, Bình Định", info: "Trọ 1: Giá rẻ, gần trung tâm, đầy đủ tiện nghi." },
        { address: "15 Lê Lợi, TP Quy Nhơn, Bình Định", info: "Trọ 2: An ninh tốt, thuận tiện đi lại." },
        { address: "120 Đống Đa, TP Quy Nhơn, Bình Định", info: "Trọ 3: Giá cả phải chăng, gần trường học." },
        { address: "50 Nguyễn Huệ, TP Quy Nhơn, Bình Định", info: "Trọ 4: Nhà mới, rộng rãi, thoáng mát." }
    ]; // Danh sách các địa chỉ và thông tin trọ

    var map = L.map('map').setView([13.779, 109.219], 14); // Trung tâm bản đồ tại Quy Nhơn

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var userLat = position.coords.latitude;
            var userLon = position.coords.longitude;

            L.marker([userLat, userLon]).addTo(map)
                .bindPopup("Vị trí hiện tại của bạn")
                .openPopup();

            // Hàm tính khoảng cách giữa hai tọa độ
            function calculateDistance(lat1, lon1, lat2, lon2) {
                var R = 6371; // Bán kính Trái Đất (km)
                var dLat = (lat2 - lat1) * Math.PI / 180;
                var dLon = (lon2 - lon1) * Math.PI / 180;
                var a = 
                    0.5 - Math.cos(dLat)/2 + 
                    Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * 
                    (1 - Math.cos(dLon)) / 2;

                return R * 2 * Math.asin(Math.sqrt(a));
            }

            locations.forEach(function(location) {
                var encodedAddress = encodeURIComponent(location.address);
                var geocodeUrl = 'https://nominatim.openstreetmap.org/search?format=json&q=' + encodedAddress;

                fetch(geocodeUrl)
                    .then(response => response.json())
                    .then(data => {
                        if (data.length > 0) {
                            var latitude = parseFloat(data[0].lat);
                            var longitude = parseFloat(data[0].lon);

                            // Tính khoảng cách từ vị trí người dùng đến phòng trọ
                            var distance = calculateDistance(userLat, userLon, latitude, longitude);

                            // Kiểm tra nếu khoảng cách nhỏ hơn hoặc bằng 1.2 km
                            if (distance <= 1.2) {
                                L.marker([latitude, longitude]).addTo(map)
                                    .bindPopup(`${location.address} <br> ${location.info} <br> Khoảng cách: ${distance.toFixed(2)} km`)
                                    .on('click', function() {
                                        document.getElementById('details').innerHTML = `<h3>${location.address}</h3><p>${location.info}</p><p>Khoảng cách: ${distance.toFixed(2)} km</p>`;
                                    });
                            }
                        } else {
                            console.error("Location not found for:", location.address);
                        }
                    })
                    .catch(error => {
                        console.error("Error fetching geocode data for:", location.address, error);
                    });
            });
        }, function(error) {
            console.error("Geolocation error:", error);
        });
    } else {
        console.error("Geolocation is not supported by this browser.");
    }
});
