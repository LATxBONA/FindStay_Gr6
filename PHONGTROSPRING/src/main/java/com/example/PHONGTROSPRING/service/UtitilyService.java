package com.example.PHONGTROSPRING.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
//
//import com.example.PHONGTROSPRING.entities.Locations;
//import com.example.PHONGTROSPRING.repository.LocationRepository;

import com.example.PHONGTROSPRING.entities.LocationsDistrict;
import com.example.PHONGTROSPRING.request.RequestThanhToan;

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

	public static BigDecimal tinhtien(RequestThanhToan request) {
		BigDecimal tien = BigDecimal.ZERO;
		double gia = 0;
		if (request.getLoaitin() == 0) {
			gia = 0;
		} else if (request.getLoaitin() == 1) {
			gia = 2000;
		} else if (request.getLoaitin() == 2) {
			gia = 10000;
		} else if (request.getLoaitin() == 3) {
			gia = 20000;
		} else if (request.getLoaitin() == 4) {
			gia = 30000;
		} else if (request.getLoaitin() == 5) {
			gia = 50000;
		}

		if (request.getGoitime().equals("Ngày")) {
			tien.valueOf(gia * request.getSongay());
		} else if (request.getGoitime().equals("Tuần")) {
			tien.valueOf((gia * request.getSongay() * 7) * 0.95);
		} else if (request.getGoitime().equals("Tháng")) {
			tien.valueOf((gia * request.getSongay() * 30) * 0.9);
		}
		return tien;
	}

	public static LocalDateTime plusday(RequestThanhToan requesttt) {
		LocalDateTime localdate = LocalDateTime.now();
		if (requesttt.getGoitime().equals("ngay")) {
			localdate = localdate.plusDays(requesttt.getSongay());
		} else if (requesttt.getGoitime().equals("tuan")) {
			localdate = localdate.plusDays(requesttt.getSongay() * 7);
		} else if (requesttt.getGoitime().equals("thang")) {
			localdate = localdate.plusDays(requesttt.getSongay() * 30);
		}
		return localdate;
	}

}
