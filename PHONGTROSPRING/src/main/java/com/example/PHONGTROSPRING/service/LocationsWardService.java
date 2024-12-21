package com.example.PHONGTROSPRING.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.LocationsDistrict;
import com.example.PHONGTROSPRING.entities.LocationsWard;
import com.example.PHONGTROSPRING.repository.LocationsWardRepository;

@Service
public class LocationsWardService {
	@Autowired
	LocationsWardRepository locationsWardRepository;
	
	public List<LocationsWard> getAllWard(LocationsDistrict locationsDistrict) {
		return locationsWardRepository.findByLocationDistrict(locationsDistrict);
	}
	
	public LocationsWard getLocationWard(int id) {
		return locationsWardRepository.findById(id).orElseThrow(()->new RuntimeException("error"));
	}
}
