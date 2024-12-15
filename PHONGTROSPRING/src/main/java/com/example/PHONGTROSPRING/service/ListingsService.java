package com.example.PHONGTROSPRING.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.Images;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.Locations;
import com.example.PHONGTROSPRING.entities.RoomTypes;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.repository.ListingsRepository;
import com.example.PHONGTROSPRING.repository.UserRepository;

@Service
public class ListingsService {

	@Autowired
	private ListingsRepository listingRepository;

	// Lấy tất cả danh sách room để render lên view
	public List<Listings> getAllListings() {
		List<Listings> list = new ArrayList<>();

		list = listingRepository.findAll();

		return list;
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

	public List<Listings> getRoomFeatured(int locationId) {
		return listingRepository.findAllListingsOrderByPostTypeAndCreatedAt(locationId);
	}

	public List<Listings> getNewRoom(int locationId) {
		return listingRepository.findAllListingsFollowLocationAndCreatedAt(locationId);
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

	public String[] cutStringDescription(String substr) {
		String[] words = substr.split("\\.");

		return words;
	}

	public String date(LocalDateTime date) {
		LocalDateTime now = LocalDateTime.now();
		long timeSeconds = ChronoUnit.SECONDS.between(date, now);
		long timeMinutes = ChronoUnit.MINUTES.between(date, now);
		long timeHours = ChronoUnit.HOURS.between(date, now);
		long timeDays = ChronoUnit.DAYS.between(date, now);

		String dateTime = "";
		if (timeSeconds <= 60) {
			dateTime = timeSeconds + " giây trước";
		} else if (timeMinutes <= 60) {
			dateTime = timeMinutes + " phút trước";
		} else if (timeHours <= 24) {
			dateTime = timeHours + " giờ trước";
		} else {
			dateTime = timeDays + " ngày trước";
		}

		return dateTime;
	}

	public List<String> dateArray(List<Listings> listing) {
		List<String> listDate = new ArrayList<>();
		for (Listings item : listing) {
			listDate.add(date(item.getCreatedAt()));
		}

		return listDate;
	}

	public Listings getListingById(int id) {
		return listingRepository.getById(id);
	}

	public Page<Listings> searchTin(String status, int postType, String title, User user, Pageable pageable) {
		return listingRepository.findByStatusOrPostTypeOrTitleAndUser(status, postType, title, user, pageable);
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

}
