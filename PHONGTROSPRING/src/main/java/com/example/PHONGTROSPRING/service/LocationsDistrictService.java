package com.example.PHONGTROSPRING.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.LocationsDistrict;
import com.example.PHONGTROSPRING.repository.LocationsDistrictRepository;

@Service
public class LocationsDistrictService {
	
	@Autowired
	private LocationsDistrictRepository locationsDistrictRepository;

	public List<LocationsDistrict> getDistrict(int city_id) {
		return locationsDistrictRepository.getDistrict(city_id);
	}
	public LocationsDistrict get1District(int district_id) {
		return locationsDistrictRepository.findById(district_id).orElseThrow(() -> new RuntimeException("District not found"));
	}
	 // Thêm method mới để tìm district_id theo tên
    public int findDistrictIdByName(String districtName) {
        LocationsDistrict district = locationsDistrictRepository.findByDistrict(districtName)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy quận/huyện: " + districtName));
        return district.getDistrict_id();
    }
}
