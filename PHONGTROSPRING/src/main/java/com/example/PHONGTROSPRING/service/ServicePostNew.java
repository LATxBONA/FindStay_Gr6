package com.example.PHONGTROSPRING.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.PHONGTROSPRING.entities.Images;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.ListingsFeatures;
import com.example.PHONGTROSPRING.entities.LocationsCity;
import com.example.PHONGTROSPRING.entities.LocationsDistrict;
import com.example.PHONGTROSPRING.entities.LocationsWard;
//import com.example.PHONGTROSPRING.entities.Locations;
import com.example.PHONGTROSPRING.entities.RoomTypes;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.repository.ImagesRepository;
import com.example.PHONGTROSPRING.repository.ListingsFeaturesRepository;
import com.example.PHONGTROSPRING.repository.ListingsRepository;
import com.example.PHONGTROSPRING.repository.LocationsCityRepository;
import com.example.PHONGTROSPRING.repository.LocationsDistrictRepository;
import com.example.PHONGTROSPRING.repository.LocationsWardRepository;
import com.example.PHONGTROSPRING.repository.PaymentHistoryRepository;
//import com.example.PHONGTROSPRING.repository.LocationRepository;
import com.example.PHONGTROSPRING.repository.RoomTypesRepository;
import com.example.PHONGTROSPRING.repository.UserRepository;
import com.example.PHONGTROSPRING.request.RequestPostNew;
import com.example.PHONGTROSPRING.request.RequestThanhToan;

@Service
public class ServicePostNew {


	@Autowired
	private ListingsRepository ListingsRepository;

	@Autowired
	private UserRepository UserRepository;

	@Autowired
	private RoomTypesRepository RoomTypesRepository;

	@Autowired
	private ImagesRepository ImagesRepository;

	@Autowired
	private LocationsCityRepository locationsCityRepository;

	@Autowired
	private LocationsDistrictRepository locationsDistrictRepository;

	@Autowired
	private LocationsWardRepository locationsWardRepository;

	@Autowired
	private ListingsFeaturesRepository ListingsFeaturesRepository;
	
	@Autowired
	private PaymentHistoryRepository paymentHistoryRepository;

	public List<RoomTypes> getRoomTypes() {

		List<RoomTypes> roomtypes = new ArrayList<>();
		roomtypes = RoomTypesRepository.findAll();

		return roomtypes;
	}


	public boolean postNew(RequestPostNew request, RequestThanhToan requesttt, ListingsFeatures listingsFeatures) {

		Listings listing = new Listings();
		LocalDateTime localdate = UtitilyService.plusday(LocalDateTime.now(),requesttt);
		BigDecimal tiensd = UtitilyService.tinhtien(requesttt);
		BigDecimal tienuser = request.getUser().getBalance().subtract(tiensd);

		
		User us = request.getUser();
		if(us.getBalance().compareTo(tiensd)<0) {
			return false;
		}
		
		us.setBalance(tienuser);
		UserRepository.save(us);
		
		RoomTypes roomtypes = RoomTypesRepository.findById(request.getRoomTypeid())
				.orElseThrow(() -> new RuntimeException("Không có kiểu phòng"));

		LocationsCity city = locationsCityRepository.findById(request.getCity_id())
				.orElseThrow(() -> new RuntimeException("Không có city"));
		
		LocationsDistrict district = locationsDistrictRepository.findById(request.getDistrict_id())
				.orElseThrow(() -> new RuntimeException("Không có district"));

		LocationsWard ward = locationsWardRepository.findById(request.getWard_id())
				.orElseThrow(() -> new RuntimeException("Không có city"));
		listing.setUser(request.getUser());
		listing.setTitle(request.getTitle());
		listing.setDescription(request.getDescription());
		listing.setPrice(request.getPrice());
		listing.setArea(request.getArea());
		listing.setLocation_city(city);
		listing.setLocation_district(district);
		listing.setLocation_ward(ward);
		listing.setAddress(request.getAddress());
		listing.setRoomType(roomtypes);
		listing.setStatus("Chờ duyệt");
		listing.setCreatedAt();
		listing.setObject(request.getObject());
		listing.setExpiryDate(localdate);
		listing.setPostType(requesttt.getLoaitin());
		ListingsRepository.save(listing);
		listingsFeatures.setListings(listing);
		ListingsFeaturesRepository.save(listingsFeatures);
		paymentHistoryRepository.save(UtitilyService.payment(request.getUser(), tiensd, "Đăng tin mới", listing));
		for (MultipartFile file : request.getUrlAnh()) {
			Images images = new Images();
			try {
				images.setListing(listing);
				images.setImageUrl(file.getBytes());
				ImagesRepository.save(images);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;

	}

	public List<byte[]> getanh(int id) {
		Listings listting = ListingsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Tìm không có listting"));
		List<Images> listimage = ImagesRepository.findByListing(listting);
		List<byte[]> listurl = new ArrayList<>();
		for (Images img : listimage) {
			listurl.add(img.getImageUrl());
		}

		return listurl;
	}


}
