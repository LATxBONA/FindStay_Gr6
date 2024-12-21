package com.example.PHONGTROSPRING.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.PHONGTROSPRING.entities.LocationsDistrict;
import com.example.PHONGTROSPRING.entities.LocationsWard;
import com.example.PHONGTROSPRING.service.addLocationService;

@RestController
public class AddLocationRestController {

	@Autowired
	private addLocationService service;

	@GetMapping("/addlocationCity/{city}")
	public String saveCity(@PathVariable String city) {
		return service.addCity(city);
	}

	@GetMapping("/saveDistricts/{city}/{district}")
	public String saveDistrict(@PathVariable String city, @PathVariable String district) {
		return service.saveDisctrict(city, district);
	}

	@GetMapping("/saveWards/{district}/{ward}")
	public String saveWard(@PathVariable String district, @PathVariable String ward) {
		return service.saveWard(district, ward);
	}
	
	@GetMapping("/getDistrictsByCity/{cityId}")
	public List<LocationsDistrict> getDistrictsByCity(@PathVariable int cityId) {
	    return service.getDistrictsByCity(cityId);
	}

	@GetMapping("/getWardsByDistrict/{districtId}")
	public List<LocationsWard> getWardsByDistrict(@PathVariable int districtId) {
	    return service.getWardsByDistrict(districtId);
	}
	

}
