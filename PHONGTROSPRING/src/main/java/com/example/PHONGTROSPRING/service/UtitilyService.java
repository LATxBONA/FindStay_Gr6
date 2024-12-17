package com.example.PHONGTROSPRING.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
//
//import com.example.PHONGTROSPRING.entities.Locations;
//import com.example.PHONGTROSPRING.repository.LocationRepository;

import com.example.PHONGTROSPRING.entities.LocationsDistrict;

@Service
public class UtitilyService {

	public static List<LocationsDistrict> changeDistrictName(List<LocationsDistrict> list_district_response) {
		for (LocationsDistrict item : list_district_response) {
			if (item.getDistrict().contains("Huyện")) {
				item.setDistrict(item.getDistrict().substring(6));
			} else if (item.getDistrict().contains("Thị xã")) {
				item.setDistrict("Tx. " + item.getDistrict().substring(6));
			} else if (item.getDistrict().contains("Thành phố")) {
				item.setDistrict("Tp. " + item.getDistrict().substring(10));
			} else if (item.getDistrict().contains("Quận")) {
				if (!Pattern.compile("[0-9]").matcher(item.getDistrict()).find()) {
					item.setDistrict("Q. " + item.getDistrict().substring(5));
				} 
			}
		}
		return list_district_response;
	}

}
