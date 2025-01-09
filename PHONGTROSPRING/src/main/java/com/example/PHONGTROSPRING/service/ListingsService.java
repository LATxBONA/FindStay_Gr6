package com.example.PHONGTROSPRING.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.Images;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.ListingsFeatures;
import com.example.PHONGTROSPRING.entities.PaymentHistory;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.repository.ImagesRepository;
import com.example.PHONGTROSPRING.repository.ListingsFeaturesRepository;
import com.example.PHONGTROSPRING.repository.ListingsRepository;
import com.example.PHONGTROSPRING.repository.PaymentHistoryRepository;
import com.example.PHONGTROSPRING.response.ListingsResponse;

@Service
public class ListingsService {

	@Autowired
	private ListingsRepository listingRepository;

	@Autowired
	private ImagesRepository imagesRepository;

	@Autowired
	private ListingsFeaturesRepository listingsFeaturesRepository;
	
	@Autowired
	private PaymentHistoryRepository paymentHistoryRepository;

	private Pageable pageable_5 = PageRequest.of(0, 5);
	private Pageable pageable_1 = PageRequest.of(0, 1);

	// Lấy tất cả danh sách room để render lên view
	public List<Listings> getAllListings() {
		List<Listings> list = new ArrayList<>();

		list = listingRepository.findAll();
		list.sort(Comparator.comparing(Listings::getCreatedAt).reversed());
		return list;
	}
	
	public ListingsFeatures getListingsFeatures(int id) {
		return listingRepository.findListingsFeatures(id);
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

	// Lấy danh sách theo room type từ cao xuống thấp- trang home
    public Page<ListingsResponse> getAllListingsApproved(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return listingRepository.getAllListingsApproved(pageable);
    }

    // Lấy danh sách theo thời gian mới nhất - trang home
    public Page<ListingsResponse> getAllListingsByNewest(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return listingRepository.getAllListingsByNewest(pageable);
    }
    
 
 // Lấy danh sách theo thời gian mới nhất cho toàn quốc
    public Page<ListingsResponse> getListingsByNewest(int roomtype_id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return listingRepository.getListingsByNewest(roomtype_id, pageable);
    }

    // Lấy danh sách theo room type từ cao xuống thấp cho toàn quốc
    public Page<ListingsResponse> getListingsNationWide(int roomtype_id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return listingRepository.getListingsNationWide(roomtype_id, pageable);
    }

    // Lấy danh sách theo thời gian mới nhất cho thành phố cụ thể
    public Page<ListingsResponse> getListingsByNewestAndCity(int roomtype_id, int city_id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return listingRepository.getListingsByNewestAndCity(roomtype_id, city_id, pageable);
    }

    // Lấy danh sách mặc định cho thành phố cụ thể
    public Page<ListingsResponse> getListings(int roomtype_id, int city_id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return listingRepository.getListings(roomtype_id, city_id, pageable);
    }
    
    public Page<ListingsResponse> getListingsByDistrictAndOrder(
            int roomtype_id, 
            int city_id,
            int district_id,  // Thay đổi tham số thành district_id
            String orderby,
            int page, 
            int size) {
                
            Pageable pageable = PageRequest.of(page, size);
            return listingRepository.findByDistrictAndOrderBy(
                roomtype_id, 
                city_id, 
                district_id,  // Truyền district_id
                orderby,
                pageable
            );
        }

	// Set ảnh cho từng tin
	public List<ListingsResponse> setImageForListingsResponse(Page<ListingsResponse> listingResponse) {
		List<ListingsResponse> list = new ArrayList<>();
		for (ListingsResponse item : listingResponse) {
			List<String> images = findImageByItemId(item.getItemId());
			if (!images.isEmpty()) {
				item.setImageUrl(images.get(0));
			}
			list.add(item);
		}
		return list;
	}

	public boolean danglai(User user, int id) {
		Listings listting = listingRepository.findById(id).orElseThrow(() -> new RuntimeException("error"));
		if(listting.getExpiryDate().isBefore(LocalDateTime.now())) {
			return false;
		}
		else {
			listting.setStatus("Chờ duyệt");
			listingRepository.save(listting);
			return true;
		}

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

	/*
	 * public List<Listings> getListingsByLAT( BigDecimal minPrice, BigDecimal
	 * maxPrice, BigDecimal minArea, BigDecimal maxArea, String roomType, String
	 * city_id, String district_id, String ward_id ) { // Xử lý giá trị null hoặc
	 * rỗng String roomTypeFilter = (roomType == null || roomType.isEmpty()) ? "%" :
	 * roomType; String cityIdFilter = (city_id == null || city_id.isEmpty()) ? "%"
	 * : city_id; String districtIdFilter = (district_id == null ||
	 * district_id.isEmpty()) ? "%" : district_id; String wardIdFilter = (ward_id ==
	 * null || ward_id.isEmpty()) ? "%" : ward_id;
	 * 
	 * // Gọi repository return listingRepository.findListingsByLAT( minPrice,
	 * maxPrice, minArea, maxArea, roomTypeFilter, cityIdFilter, districtIdFilter,
	 * wardIdFilter ); }
	 */
	public Page<ListingsResponse> getListingsByLAT(BigDecimal minPrice, BigDecimal maxPrice, BigDecimal minArea,
			BigDecimal maxArea, Integer roomType, Integer city_id, Integer district_id, Integer ward_id,
			Pageable pageable) {
		// Kiểm tra giá trị null hoặc rỗng và thay thế bằng giá trị mặc định (-1)
		Integer roomTypeFilter = (roomType == null || roomType <= 0) ? -1 : roomType;
		Integer cityIdFilter = (city_id == null || city_id <= 0) ? -1 : city_id;
		Integer districtIdFilter = (district_id == null || district_id <= 0) ? -1 : district_id;
		Integer wardIdFilter = (ward_id == null || ward_id <= 0) ? -1 : ward_id;

		// Gọi repository
		return listingRepository.findListingsByLAT(minPrice, maxPrice, minArea, maxArea, roomTypeFilter, cityIdFilter,
				districtIdFilter, wardIdFilter, pageable);
	}

	public ListingsFeatures getListingsFeatures(Listings listings) {
		return listingsFeaturesRepository.findByListings(listings);
	}
	
	public List<Listings> getListingByStatus(String status) {
		return listingRepository.getListingByStatus(status);
	}
	
	public Listings updateListing(Integer itemId, String newStatus) {
		Listings listing = listingRepository.findById(itemId).orElse(null);
		
		listing.setStatus(newStatus);
		
		return listingRepository.save(listing);
	}
	
	public void deleteListingsByUserId(String userId) {
	    List<Listings> userListings = listingRepository.findByUser_UserId(userId);
	    listingRepository.deleteAll(userListings);
	}
	
	public void deleteListingFeaturesByUserId(String userId) {
		List<Listings> userListings = listingRepository.findByUser_UserId(userId);
		
		for (Listings listing : userListings) {
			ListingsFeatures features = listingsFeaturesRepository.findByListings(listing);
			if (features != null) {
				listingsFeaturesRepository.delete(features);
			}
		}
	}
	
	public void deleteImagesByUserId(String userId) {
		// Lấy danh sách listings của user
		List<Listings> userListings = listingRepository.findByUser_UserId(userId);
		
		// Xóa images của từng listing
		for (Listings listing : userListings) {
			List<Images> images = imagesRepository.findByListingItemId(listing.getItemId());
			if (!images.isEmpty()) {
				imagesRepository.deleteAll(images);
			}
		}
	}
	
	public void deletePaymentHistoryByUserId(String userId) {
		// Lấy danh sách listings của user
		List<Listings> userListings = listingRepository.findByUser_UserId(userId);
		
		// Xóa payment history của từng listing
		for (Listings listing : userListings) {
			List<PaymentHistory> payments = paymentHistoryRepository.findByListings(listing);
			if (!payments.isEmpty()) {
				paymentHistoryRepository.deleteAll(payments);
			}
		}
	}
}
