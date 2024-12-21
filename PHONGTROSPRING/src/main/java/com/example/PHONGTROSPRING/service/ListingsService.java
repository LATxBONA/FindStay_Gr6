package com.example.PHONGTROSPRING.service;

import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.Images;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.ListingsFeatures;
import com.example.PHONGTROSPRING.entities.LocationsDistrict;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.repository.ImagesRepository;
import com.example.PHONGTROSPRING.repository.ListingsFeaturesRepository;
import com.example.PHONGTROSPRING.repository.ListingsRepository;

import com.example.PHONGTROSPRING.repository.LocationsDistrictRepository;
import com.example.PHONGTROSPRING.response.ListingsResponse;

import com.example.PHONGTROSPRING.repository.UserRepository;

@Service
public class ListingsService {

	@Autowired
	private ListingsRepository listingRepository;

	@Autowired
	private ImagesRepository imagesRepository;
	
	@Autowired
	private ListingsFeaturesRepository listingsFeaturesRepository;

	private Pageable pageable_5 = PageRequest.of(0, 5);
	private Pageable pageable_1 = PageRequest.of(0, 1);

	// Lấy tất cả danh sách room để render lên view
	public List<Listings> getAllListings() {
		List<Listings> list = new ArrayList<>();

		list = listingRepository.findAll();

		return list;
	}

	public Listings findById(int roomId) {
		return listingRepository.findById(roomId).orElse(null);
	}

	public String[] cutStringDescription(String substr) {
		String[] words = substr.split("\\.");

		return words;
	}

	// lấy tất cả ảnh của phòng hiện tại
	public List<String> getImages(int item_id) {
		List<byte[]> imageBytesList = imagesRepository.findByItemId(item_id);
		return encryptionImages(imageBytesList);
	}

	// lấy 5 phòng làm tin nổi bật
	public List<ListingsResponse> findAllListingsFeatured(int item_id, int roomType_id, int district_id) {
		return listingRepository.findAllListingsFeatured(item_id, roomType_id, district_id, pageable_5);
	}

	// lấy 5 phòng làm tin vừa đăng
	public List<ListingsResponse> findAllNewsJustPosted(int item_id, int roomType_id, int district_id) {
		return listingRepository.findAllNewsJustPosted(item_id, roomType_id, district_id, pageable_5);
	}

	// lấy 1 ảnh của từng phòng trong tin nổi bật và tin vừa đăng
	public List<String> findImageByItemId(int item_id) {
		List<byte[]> imageBytesList = imagesRepository.findByItemIdFromFeaturedNew(item_id, pageable_1);
		return encryptionImages(imageBytesList);
	}

	// mã hóa ảnh
	public List<String> encryptionImages(List<byte[]> imageBytesList) {
		List<String> base64Images = new ArrayList<>();
		for (byte[] imageBytes : imageBytesList) {
			base64Images.add(Base64.getEncoder().encodeToString(imageBytes));
		}
		return base64Images;
	}

	public Page<Listings> getListingByUser(User user, Pageable pageable) {
		return listingRepository.findByUser(user, pageable);
	}

	/*
	 * public void addSampleListings() {
	 * 
	 * // Chú ý: khi set id cho các user, location, roomtype thì lấy id trong sql vì
	 * id // này là chuỗi và sinh ra ngẫu nhiên chứ không phải số User user = new
	 * User(); user.setUserId("53c5fcc8-cd81-43ef-a911-6bf48473f7eb");
	 * 
	 * Locations location = new Locations(); location.setLocationId(1);
	 * 
	 * RoomTypes roomType = new RoomTypes(); roomType.setRoomTypeId(1);
	 * 
	 * Listings listing = new Listings();
	 * 
	 * listing.setUser(user); listing.setTitle("Phòng cho thuê gần hồ Hoàn Kiếm");
	 * listing.setDescription("Phòng đẹp, có đầy đủ tiện nghi, gần trung tâm.");
	 * listing.setPrice(new BigDecimal("3000000")); listing.setArea(new
	 * BigDecimal("20.5")); listing.setLocation(location);
	 * listing.setAddress("15 đường Lê Duẩn"); listing.setRoomType(roomType);
	 * listing.setCreatedAt(); listing.setStatus("Chờ duyệt");
	 * listing.setObject("Tất cả"); listing.setPostType(2);
	 * listing.setExpiryDate(5); listing.setUpdatedAt();
	 * 
	 * listingRepository.save(listing); }
	 */

	public Listings getRoomById(int roomId) {
		return listingRepository.findById(roomId).orElse(null);
	}

	public List<Images> getImageFollowRoomFeatured(List<Images> list) {
		List<Images> imgFeaturedNew = new ArrayList<>();

		int temp = -1;

		for (Images itemCurrent : list) {
			if (itemCurrent.getListing().getItemId() != temp) {
				imgFeaturedNew.add(itemCurrent);
				temp = itemCurrent.getListing().getItemId();
			}
		}

		return imgFeaturedNew;
	}

	public List<Images> getImageFollowNewRoom(List<Images> list) {
		List<Images> imgFeaturedNew = new ArrayList<>();

		int temp = -1;

		for (Images itemCurrent : list) {
			if (itemCurrent.getListing().getItemId() != temp) {
				imgFeaturedNew.add(itemCurrent);
				temp = itemCurrent.getListing().getItemId();
			}
		}

		return imgFeaturedNew;
	}

	/*
	 * public String date(LocalDateTime date) { LocalDateTime now =
	 * LocalDateTime.now(); long timeSeconds = ChronoUnit.SECONDS.between(date,
	 * now); long timeMinutes = ChronoUnit.MINUTES.between(date, now); long
	 * timeHours = ChronoUnit.HOURS.between(date, now); long timeDays =
	 * ChronoUnit.DAYS.between(date, now);
	 * 
	 * String dateTime = ""; if (timeSeconds <= 60) { dateTime = timeSeconds +
	 * " giây trước"; } else if (timeMinutes <= 60) { dateTime = timeMinutes +
	 * " phút trước"; } else if (timeHours <= 24) { dateTime = timeHours +
	 * " giờ trước"; } else { dateTime = timeDays + " ngày trước";
	 * 
	 * } return base64Images; }
	 */

	public int getQuantityPost() {
		return listingRepository.getQuantityPost();
	}

	public Page<ListingsResponse> getListings(int roomtype_id, int city_id, int page, int size) {
		return listingRepository.getListings(roomtype_id, city_id, PageRequest.of(page, size));
	}

	public Page<ListingsResponse> getListingsNationWide(int roomtype_id, int page, int size) {
		return listingRepository.getListingsNationWide(roomtype_id, PageRequest.of(page, size));
	}

	/*
	 * public Listings getListingById(int id) { return
	 * listingRepository.getById(id); }
	 */

	public Page<Listings> searchTin(String status, int postType, String title, User user, Pageable pageable) {
		return listingRepository.getListingBySearchtin(status, postType, title, user, pageable);
	}

	public void antin(int id) {
		Listings listting = listingRepository.findById(id).orElseThrow(() -> new RuntimeException("error"));
		listting.setStatus("Ẩn tin");

		listingRepository.save(listting);

	}

	public void danglai(int id) {
		Listings listting = listingRepository.findById(id).orElseThrow(() -> new RuntimeException("error"));
		listting.setStatus("Đã duyệt");

		listingRepository.save(listting);

	}

	/*
	 * @Scheduled(fixedRate = 30000) public void checkhethan() { LocalDateTime now =
	 * LocalDateTime.now(); for (Listings listing : getAllListings()) { if
	 * (now.isAfter(listing.getExpiryDate())) { listing.setStatus("Hết hạn"); } } }
	 */

	// Tú làm tiềm kiếm nè
	public List<Listings> getListingsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
		return listingRepository.findListingsByPriceRange(minPrice, maxPrice);
	}

	public List<Listings> getListingsByLAT(BigDecimal minPrice, BigDecimal maxPrice, BigDecimal minArea,
			BigDecimal maxArea, String roomType) {
		return listingRepository.findListingsByLAT(minPrice, maxPrice, minArea, maxArea, roomType);
	}

	
	public ListingsFeatures getListingsFeatures(Listings listings) {
		return listingsFeaturesRepository.findByListings(listings);
	}
}
