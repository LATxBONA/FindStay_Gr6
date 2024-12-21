package com.example.PHONGTROSPRING.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.*;
import com.example.PHONGTROSPRING.repository.*;

@Service
public class addLocationService {

	@Autowired
	private LocationsCityRepository cityRepo;

	@Autowired
	private LocationsDistrictRepository districtRepo;

	@Autowired
	private LocationsWardRepository wardRepo;

	@Autowired
	private RoomTypesRepository roomRepo;

	public String addCity(String city) {

		LocationsCity newCity = new LocationsCity();

		if (!cityRepo.findByCity("Thành phố Hồ Chí Minh").isPresent()
				|| !cityRepo.findByCity("Thành phố Hà Nội").isPresent()
				|| !cityRepo.findByCity("Tỉnh Bình Định").isPresent()) {
			newCity.setCity(city);
		}

		if (cityRepo.findByCity("Thành phố Hồ Chí Minh").isPresent()
				|| cityRepo.findByCity("Thành phố Hà Nội").isPresent()
				|| cityRepo.findByCity("Tỉnh Bình Định").isPresent()) {
			return city + " đã tồn tại";
		}

		return cityRepo.save(newCity) != null ? "Thành công" : "Thất bại";
	}

	public String saveDisctrict(String cityName, String district) {

		Optional<LocationsCity> city = cityRepo.findByCity(cityName);

		if (city.isPresent()) {
			if (!districtRepo.findByDistrict(district).isPresent()) {

				LocationsDistrict newDistrict = new LocationsDistrict();
				newDistrict.setDistrict(district);

				LocationsCity cityCurrent = city.get();
				newDistrict.setLocation_city(cityCurrent);

				districtRepo.save(newDistrict);

				return "Thành công";
			}
			return "Đã có district";
		}
		return "Không có city";
	}

	public String saveWard(String districtName, String wardName) {

		Optional<LocationsDistrict> district = districtRepo.findByDistrict(districtName);

		if (district.isPresent()) {

			if (!wardRepo.findByWard(wardName).isPresent()) {
				LocationsDistrict districCurrent = district.get();

				LocationsWard ward = new LocationsWard();

				ward.setWard(wardName);
				ward.setLocation_district(districCurrent);

				wardRepo.save(ward);
				
				return "Thành công";
			}

			addRoomType();
			
			return "Đã có ward";
		}
		return "Không có district";
	}

	public void addRoomType() {

		Optional<RoomTypes> checkRoom1 = roomRepo.searchRoomTypes("Phòng trọ");
		Optional<RoomTypes> checkRoom2 = roomRepo.searchRoomTypes("Nhà nguyên căn");
		Optional<RoomTypes> checkRoom3 = roomRepo.searchRoomTypes("Căn hộ");
		Optional<RoomTypes> checkRoom4 = roomRepo.searchRoomTypes("Mặt bằng");

		RoomTypes room1 = new RoomTypes();
		RoomTypes room2 = new RoomTypes();
		RoomTypes room3 = new RoomTypes();
		RoomTypes room4 = new RoomTypes();

		if (!checkRoom1.isPresent()) {
			room1.setRoomTypeName("Phòng trọ");
			roomRepo.save(room1);
		}

		if (!checkRoom2.isPresent()) {
			room2.setRoomTypeName("Nhà nguyên căn");
			roomRepo.save(room2);
		}

		if (!checkRoom3.isPresent()) {
			room3.setRoomTypeName("Căn hộ");
			roomRepo.save(room3);
		}

		if (!checkRoom4.isPresent()) {
			room4.setRoomTypeName("Mặt bằng");
			roomRepo.save(room4);
		}
	}
	
	public List<LocationsDistrict> getDistrictsByCity(int cityId) {
	    return districtRepo.getDistrict(cityId);
	}

	public List<LocationsWard> getWardsByDistrict(int districtId) {
	    return wardRepo.findAll().stream()
	            .filter(w -> w.getLocation_district().getDistrict_id() == districtId)
	            .toList();
	}

	
}
