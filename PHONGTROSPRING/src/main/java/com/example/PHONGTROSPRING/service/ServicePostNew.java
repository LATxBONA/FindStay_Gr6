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
//import com.example.PHONGTROSPRING.repository.LocationRepository;
import com.example.PHONGTROSPRING.repository.RoomTypesRepository;
import com.example.PHONGTROSPRING.repository.UserRepository;
import com.example.PHONGTROSPRING.request.RequestPostNew;
import com.example.PHONGTROSPRING.request.RequestThanhToan;

@Service
public class ServicePostNew {

	/*
	 * @Autowired private RepositoryListings repo;
	 * 
	 * public Listings post(RequestPostNew request) { Listings listing = new
	 * Listings();
	 * 
	 * 
	 * 
	 * listing.setUser_id(request.getUser_id());
	 * listing.setTitle(request.getTitle());
	 * listing.setDescription(request.getDescription());
	 * listing.setPrice(request.getPrice()); listing.setArea(request.getArea());
	 * listing.setLocation_id(request.getLocation_id());
	 * listing.setRoom_type_id(request.getRoom_type_id());
	 * listing.setCreated_at(request.getCreated_at());
	 * listing.setUpdated_at(request.getUpdated_at());
	 * listing.setStatus(request.getStatus());
	 * listing.setObject(request.getObject());
	 * listing.setAddress(request.getAddress());
	 * 
	 * 
	 * 
	 * return repo.save(listing);
	 * 
	 * }
	 */
	@Autowired
	private ListingsRepository ListingsRepository;

	@Autowired
	private UserRepository UserRepository;

//	@Autowired
//	private LocationRepository LocationRepository;
//
//	@Autowired
//	private RoomTypesRepository RoomTypesRepository;
//
//	@Autowired
//	private ImagesRepository ImagesRepository;
//
//	public List<Locations> getLocation() {
//
//		List<Locations> location = new ArrayList<>();
//		location = LocationRepository.findAll();
//
//		return location;
//	}

//	public List<RoomTypes> getRoomTypes() {
//
//		List<RoomTypes> roomtypes = new ArrayList<>();
//		roomtypes = RoomTypesRepository.findAll();
//
//		return roomtypes;
//	}
//
//	public List<String> getAllcity() {
//		List<Locations> location = new ArrayList<>();
//		List<String> list = new ArrayList<>();
//		location = LocationRepository.findAll(Sort.by(Sort.Order.asc("city")));
//
//		String temp = location.get(0).getCity();
//
//		list.add(temp);
//
//		for (int i = 0; i < location.size(); i++) {
//			if (!location.get(i).getCity().equals(temp)) {
//				temp = location.get(i).getCity();
//				list.add(temp);
//			}
//		}
//
//		return list;
//
//	}

//	public List<Locations> getAllLocations(String city) {
//		List<Locations> location = new ArrayList<>();
//		location = LocationRepository.findByCity(city);
//
//		return location;
//	}

//	public void postNew(RequestPostNew request, RequestThanhToan requesttt) {
//
//		Listings listing = new Listings();
//		LocalDateTime localdate = LocalDateTime.now();
//		if (requesttt.getGoitime().equals("ngay")) {
//			localdate.plusDays(requesttt.getSongay());
//		} else if (requesttt.getGoitime().equals("tuan")) {
//			localdate.plusDays(requesttt.getSongay() * 7);
//		} else if (requesttt.getGoitime().equals("thang")) {
//			localdate.plusDays(requesttt.getSongay() * 30);
//		}
//
//		User user = new User();
//
//		user = UserRepository.findById(request.getUserid())
//				.orElseThrow(() -> new RuntimeException("Không có user này"));
//
//		Locations location = new Locations();
//		location = LocationRepository.findById(request.getLocationid())
//				.orElseThrow(() -> new RuntimeException("Không có location"));
//
//		RoomTypes roomtypes = new RoomTypes();
//		roomtypes = RoomTypesRepository.findById(request.getRoomTypeid())
//				.orElseThrow(() -> new RuntimeException("Không có kiểu phòng"));
//
//		listing.setUser(user);
//		listing.setTitle(request.getTitle());
//		listing.setDescription(request.getDescription());
//		listing.setPrice(request.getPrice());
//		listing.setArea(request.getArea());
//		listing.setLocation(location);
//		listing.setAddress(request.getAddress());
//		listing.setRoomType(roomtypes);
//		listing.setStatus("Chờ duyệt");
//		listing.setCreatedAt();
//		listing.setObject(request.getObject());
//		listing.setExpiryDate(localdate);
//		listing.setPostType(requesttt.getLoaitin());
//		ListingsRepository.save(listing);
//		for (MultipartFile file : request.getUrlAnh()) {
//			Images images = new Images();
//			try {
//				images.setListing(listing);
//				images.setImageUrl(file.getBytes());
//				ImagesRepository.save(images);
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		user.getBalance().subtract(tinhtien(requesttt));
//
//	}
//
//	/*
//	 * public byte[] getanh(int id) { return
//	 * ImagesRepository.findById(id).orElseThrow(() -> new
//	 * RuntimeException("Image not found")).getImageUrl(); }
//	 */
//	public List<byte[]> getanh(int id) {
//		Listings listting = ListingsRepository.findById(id).orElseThrow(()-> new RuntimeException("Tìm không có listting"));
//		List<Images> listimage = ImagesRepository.findByListing(listting);
//		List<byte[]> listurl = new ArrayList<>();
//		for(Images img : listimage) {
//			listurl.add(img.getImageUrl());
//		}
//		return listurl;
//	}

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
	
	/*
	 * public List<Locations> getLocation() {
	 * 
	 * List<Locations> location = new ArrayList<>(); location =
	 * LocationRepository.findAll();
	 * 
	 * return location; }
	 */

	public List<RoomTypes> getRoomTypes() {

		List<RoomTypes> roomtypes = new ArrayList<>();
		roomtypes = RoomTypesRepository.findAll();

		return roomtypes;
	}

	/*
	 * public List<String> getAllcity() { List<Locations> location = new
	 * ArrayList<>(); List<String> list = new ArrayList<>(); location =
	 * LocationRepository.findAll(Sort.by(Sort.Order.asc("city")));
	 * 
	 * String temp = location.get(0).getCity();
	 * 
	 * list.add(temp);
	 * 
	 * for (int i = 0; i < location.size(); i++) { if
	 * (!location.get(i).getCity().equals(temp)) { temp = location.get(i).getCity();
	 * list.add(temp); } }
	 * 
	 * return list;
	 * 
	 * }
	 */

	/*
	 * public List<Locations> getAllLocations(String city) { List<Locations>
	 * location = new ArrayList<>(); location = LocationRepository.findByCity(city);
	 * 
	 * return location; }
	 */

	public void postNew(RequestPostNew request, RequestThanhToan requesttt, ListingsFeatures listingsFeatures) {

		Listings listing = new Listings();
		LocalDateTime localdate = UtitilyService.plusday(requesttt);

		request.getUser().getBalance().subtract(UtitilyService.tinhtien(requesttt));
		RoomTypes roomtypes = new RoomTypes();
		roomtypes = RoomTypesRepository.findById(request.getRoomTypeid())
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

	}

	/*
	 * public byte[] get1anh(int dunglamtcuoiid) { return
	 * ImagesRepository.findById(dunglamtcuoiid).orElseThrow(() -> new
	 * RuntimeException("Image not found")).getImageUrl(); }
	 */
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
