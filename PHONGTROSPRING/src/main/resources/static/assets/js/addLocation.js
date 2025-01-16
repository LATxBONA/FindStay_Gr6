document.addEventListener("DOMContentLoaded", function() {
	/*let i = 1;
	let j = 1;
	let k = 1;*/
	let sum_ward = 0;

	// Lấy dữ liệu từ file JSON
	$.ajax({
		url: 'https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json',
		method: 'GET',
		dataType: 'json',
		success: function(data) {
			// Chọn một số tên thành phố từ mảng dữ liệu
			let citys = [data[49], data[0], data[34]];

			// Gửi dữ liệu tới API Spring Boot
			citys.forEach(function(city) {

				if (city.Name == "Thành phố Hà Nội") {
					city.Name = city.Name.substring(10)
				}

				$.ajax({
					url: '/addlocationCity/' + encodeURIComponent(city.Name.trim()),
					method: 'GET',
					success: function(response) {
						/*console.log(i + '. Lưu ' + city.Name + ':', response);
						i++;*/

						let districts = city.Districts;

						districts.forEach(function(district) {

							sum_ward += district.Wards.length;

							$.ajax({
								url: '/saveDistricts/' + city.Name + '/' + district.Name,
								method: 'GET',
								success: function(response) {
									/*console.log(j + '.	Lưu ' + district.Name + ' của ' + city.Name + ':', response);
									j++;*/

									let wards = district.Wards;

									wards.forEach(function(ward) {
										$.ajax({
											url: '/saveWards/' + district.Name + '/' + ward.Name,
											method: 'GET',
											success: function(response) {
												/*console.log(k + ".		Lưu " + ward.Name + ' của ' + district.Name + ':', response);
												k++;*/
											},
											error: function(xhr, status, error) {
												console.error('Lỗi khi gửi dữ liệu:', error);
											}
										});
									})
								},
								error: function(xhr, status, error) {
									console.error('Lỗi khi gửi dữ liệu:', error);
								}
							});

						})
					},
					error: function(xhr, status, error) {
						console.error('Lỗi khi gửi dữ liệu:', error);
					}
				});
			});
			console.log(sum_ward)
		},
		error: function(xhr, status, error) {
			console.error('Lỗi:', error);
		}
	});


});
