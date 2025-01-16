package com.example.PHONGTROSPRING.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.PHONGTROSPRING.entities.LocationsCity;
import com.example.PHONGTROSPRING.repository.LocationsCityRepository;

@Controller
public class LocationsCityService {

	@Autowired
	private LocationsCityRepository locationsCityRepository;
	
	public List<LocationsCity> getAllCity() {
		return locationsCityRepository.findAll();
	}
	
	public int findByCityName(String cityName) {
		return locationsCityRepository.findByCityName(cityName);
	}
	
	public LocationsCity getlocationCity(int id) {
		return locationsCityRepository.findById(id).orElseThrow(()->new RuntimeException("error"));
	}
}
