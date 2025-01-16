	document.addEventListener("DOMContentLoaded", function() {
	    var location_map = document.getElementById('location_map');
	    var parts = location_map.innerText.split('-')
	        .map(function(part) {
	            return part.trim().split(' ');
	        })
	        .flat();
	
	    var stringTemp = parts.join("+");
	
	    var geocodeUrl = 'https://nominatim.openstreetmap.org/search?format=json&q=' + stringTemp;
	
	    fetch(geocodeUrl)
	        .then(response => response.json())
	        .then(data => {
	            if (data.length > 0) {
	                var latitude = parseFloat(data[0].lat);
	                var longitude = parseFloat(data[0].lon);
	
	                var map = L.map('map').setView([latitude, longitude], 13);
	
	                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	                    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
	                }).addTo(map);
	
	                L.marker([latitude, longitude]).addTo(map)
	                    .bindPopup(location_map.innerText)
	                    .openPopup();
	
	                if (navigator.geolocation) {
	                    navigator.geolocation.getCurrentPosition(function(position) {
	                        var userLat = position.coords.latitude;
	                        var userLon = position.coords.longitude;
	
	                        console.log("User's Position:", userLat, userLon); 
	
	                        L.marker([userLat, userLon]).addTo(map)
	                            .bindPopup("Vị trí hiện tại")
	                            .openPopup();
	 
	                        L.circle([userLat, userLon], { radius: 500 }).addTo(map);
	
	                        var routeUrl = `https://router.project-osrm.org/route/v1/driving/${userLon},${userLat};${longitude},${latitude}?overview=full&geometries=polyline`;
	
	                        fetch(routeUrl)
	                            .then(response => response.json())
	                            .then(routeData => {
	                                if (routeData.routes && routeData.routes.length > 0) {
	                                    var route = routeData.routes[0];
	
	                                    L.Routing.control({
	                                        waypoints: [
	                                            L.latLng(userLat, userLon),
	                                            L.latLng(latitude, longitude)
	                                        ],
	                                        routeWhileDragging: true
	                                    }).addTo(map);
	                                }
	                            })
	                            .catch(error => {
	                                console.error("Error fetching route data:", error);
	                            });
	                    }, function(error) {
	                        console.error("Geolocation error:", error);
	                    });
	                } else {
	                    console.error("Geolocation is not supported by this browser.");
	                }
	            } else {
	                console.error("Location not found.");
	            }
	        })
	        .catch(error => {
	            console.error("Error fetching geocode data:", error);
	        });
	});
