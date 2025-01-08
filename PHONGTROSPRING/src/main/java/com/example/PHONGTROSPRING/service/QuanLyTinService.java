package com.example.PHONGTROSPRING.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.PHONGTROSPRING.entities.Images;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.ListingsFeatures;
import com.example.PHONGTROSPRING.entities.LocationsCity;
import com.example.PHONGTROSPRING.entities.LocationsDistrict;
import com.example.PHONGTROSPRING.entities.LocationsWard;
import com.example.PHONGTROSPRING.entities.RoomTypes;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.repository.ImagesRepository;
import com.example.PHONGTROSPRING.repository.ListingsFeaturesRepository;
import com.example.PHONGTROSPRING.repository.ListingsRepository;
import com.example.PHONGTROSPRING.repository.LocationsCityRepository;
import com.example.PHONGTROSPRING.repository.LocationsDistrictRepository;
import com.example.PHONGTROSPRING.repository.LocationsWardRepository;
import com.example.PHONGTROSPRING.repository.PaymentHistoryRepository;
import com.example.PHONGTROSPRING.repository.RoomTypesRepository;
import com.example.PHONGTROSPRING.request.RequestPostNew;
import com.example.PHONGTROSPRING.request.RequestThanhToan;


@Service
public class QuanLyTinService {
	@Autowired
	ListingsRepository ListingsRepository;
	
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
	
	public void updateTin(int id, RequestPostNew request, ListingsFeatures listingfeature) {
		Listings listing = ListingsRepository.findByItemId(id);

		RoomTypes roomtypes = new RoomTypes();
		roomtypes = RoomTypesRepository.findById(request.getRoomTypeid())
				.orElseThrow(() -> new RuntimeException("Không có kiểu phòng"));

		LocationsCity city = locationsCityRepository.findById(request.getCity_id())
				.orElseThrow(() -> new RuntimeException("Không có city"));
		LocationsDistrict district = locationsDistrictRepository.findById(request.getDistrict_id())
				.orElseThrow(() -> new RuntimeException("Không có district"));

		LocationsWard ward = locationsWardRepository.findById(request.getWard_id())
				.orElseThrow(() -> new RuntimeException("Không có city"));
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
		listing.setUpdatedAt(LocalDateTime.now());
		listing.setObject(request.getObject());
		ListingsRepository.save(listing);
		//listingfeature.setListings(listing);
		ListingsFeaturesRepository.save(listingfeature);
		ImagesRepository.deleteByListing(id);
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
		
		ListingsRepository.save(listing);
	}
	
	public boolean giahantin(RequestThanhToan rq, int id, User user) {
		Listings listing = ListingsRepository.findByItemId(id);
		LocalDateTime dategiahan = LocalDateTime.now();
		rq.setLoaitin(listing.getPostType());
		BigDecimal tiensd = UtitilyService.tinhtien(rq);
		BigDecimal tienuser = user.getBalance().subtract(tiensd);
		if(user.getBalance().compareTo(tiensd)<0) {
			return false;
		}
		else {
			if(listing.getExpiryDate().isBefore(LocalDateTime.now())) {
				user.setBalance(tienuser);
				dategiahan = UtitilyService.plusday(listing.getExpiryDate(), rq);
			}else {
				user.setBalance(tienuser);
				dategiahan = UtitilyService.plusday(LocalDateTime.now(), rq);
	
			}
			paymentHistoryRepository.save(UtitilyService.payment(user, tiensd, "Gia hạn tin", listing));
			listing.setExpiryDate(dategiahan);
			ListingsRepository.save(listing);
			return true;
		}
	}
}
