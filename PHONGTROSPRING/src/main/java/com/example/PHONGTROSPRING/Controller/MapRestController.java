package com.example.PHONGTROSPRING.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.LocationsDistrict;
import com.example.PHONGTROSPRING.service.ListingsService;
import com.example.PHONGTROSPRING.service.LocationsDistrictService;

@RestController
@RequestMapping("/search_map")
public class MapRestController {

	@Autowired
	private LocationsDistrictService locationDistrictservice;

	@Autowired
	private ListingsService listingsService;



	@GetMapping()
	public List<Listings> searchOnMap(@RequestParam(value = "room", required = false, defaultValue = "0") Integer room,
			@RequestParam(value = "features", required = false) List<String> features,
			@RequestParam(value = "price", required = false, defaultValue = "0_9999") String price,
			@RequestParam(value = "area", required = false, defaultValue = "0_9999") String area) {

		// Kiểm tra thông tin nhận được từ request
		System.out.println("Room: " + room);
		System.out.println("Features: " + features); // Đây là danh sách các feature
		System.out.println("Price: " + price);
		System.out.println("Area: " + area);

		return listingsService.getListingOnMap(room, features, price, area);
	}
}
