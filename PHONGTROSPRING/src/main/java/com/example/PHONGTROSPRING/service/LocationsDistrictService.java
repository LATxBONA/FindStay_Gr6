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
	
}
