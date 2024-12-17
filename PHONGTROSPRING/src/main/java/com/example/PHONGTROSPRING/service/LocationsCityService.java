package com.example.PHONGTROSPRING.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.PHONGTROSPRING.repository.LocationsCityRepository;

@Controller
public class LocationsCityService {

	@Autowired
	private LocationsCityRepository locationsCityRepository;
	
	public int findByCityName(String cityName) {
		return locationsCityRepository.findByCityName(cityName);
	}
}
