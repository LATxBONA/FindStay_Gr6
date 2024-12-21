package com.example.PHONGTROSPRING.repository;


import org.springframework.data.domain.*;
import java.math.BigDecimal;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.response.ListingsResponse;


@Repository
public interface ListingsRepository extends JpaRepository<Listings, Integer>, JpaSpecificationExecutor<Listings>{

	@Query("SELECT new com.example.PHONGTROSPRING.response.ListingsResponse(l.itemId, l.title, l.price, l.createdAt, l.roomType.roomTypeName, l.location_city.city, l.location_district.district, l.location_ward.ward, l.address) FROM Listings l WHERE l.itemId != :item_id AND l.roomType.roomTypeId = :roomType_id AND l.location_district.district_id = :district_id AND l.status = 'Đã duyệt' ORDER BY l.postType DESC, l.createdAt DESC")
	List<ListingsResponse> findAllListingsFeatured(@Param("item_id") int item_id, @Param("roomType_id") int roomType_id,
			@Param("district_id") int district_id, Pageable pageable);
	

	@Query("SELECT new com.example.PHONGTROSPRING.response.ListingsResponse(l.itemId, l.title, l.price, l.createdAt, l.roomType.roomTypeName, l.location_city.city, l.location_district.district, l.location_ward.ward, l.address) FROM Listings l WHERE l.itemId != :item_id AND l.roomType.roomTypeId = :roomType_id AND l.location_district.district_id = :district_id AND l.status = 'Đã duyệt' ORDER BY l.createdAt DESC")
	List<ListingsResponse> findAllNewsJustPosted(@Param("item_id") int item_id, @Param("roomType_id") int roomType_id,
			@Param("district_id") int district_id, Pageable pageable);
	
	@Query("SELECT COUNT(l) FROM Listings l")
	int getQuantityPost();
	
	@Query("SELECT new com.example.PHONGTROSPRING.response.ListingsResponse(l.itemId, l.title, l.price, l.createdAt, l.roomType.roomTypeName, l.location_city.city, l.location_district.district, l.location_ward.ward, l.address, l.user.fullName, l.user.phoneNumber, l.postType, l.area) FROM Listings l WHERE l.roomType.roomTypeId = :roomtype_id AND l.location_city.city_id = :city_id AND l.status = 'Đã duyệt' ORDER BY l.postType DESC, l.createdAt DESC")
	Page<ListingsResponse> getListings(@Param("roomtype_id") int roomtype_id, @Param("city_id") int city_id, Pageable pageable);
	
	@Query("SELECT new com.example.PHONGTROSPRING.response.ListingsResponse(l.itemId, l.title, l.price, l.createdAt, l.roomType.roomTypeName, l.location_city.city, l.location_district.district, l.location_ward.ward, l.address, l.user.fullName, l.user.phoneNumber, l.postType, l.area) FROM Listings l WHERE l.itemId = :item_id")
	ListingsResponse getListingsByItemId(@Param("item_id")int item_id);
	
	@Query("SELECT new com.example.PHONGTROSPRING.response.ListingsResponse(l.itemId, l.title, l.price, l.createdAt, l.roomType.roomTypeName, l.location_city.city, l.location_district.district, l.location_ward.ward, l.address, l.user.fullName, l.user.phoneNumber, l.postType, l.area) FROM Listings l WHERE l.roomType.roomTypeId = :roomtype_id AND l.status = 'Đã duyệt' ORDER BY l.postType DESC, l.createdAt DESC")
	Page<ListingsResponse> getListingsNationWide(@Param("roomtype_id") int roomtype_id, Pageable pageable);

	@Query("SELECT l FROM Listings l WHERE (l.status = :status OR l.postType = :postType OR l.title = :title) AND l.user = :user")
	Page<Listings> getListingBySearchtin(String status, int postType, String title, User user, Pageable pageable);
	
	Page<Listings> findByUser(User user,Pageable pageable);
	
	Listings findByItemId(int itemId);

	@Query("SELECT l FROM Listings l WHERE l.price BETWEEN :minPrice AND :maxPrice ORDER BY l.price ASC")
	List<Listings> findListingsByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
	
/*	@Query(
 // value Đây là nơi bạn định nghĩa câu truy vấn
		    value = "SELECT * FROM listings WHERE price BETWEEN :minPrice AND :maxPrice ORDER BY price ASC", 
		    nativeQuery = true //câu này là để Xác định rằng đây là một câu lệnh SQL thuần
		)
		List<Listings> findListingsByPriceRangeNative(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
													// ánh xạ minPrice của câu truy vấn vào tham số của hàm java
	*/
	

	
	@Query(
		    value = "SELECT * FROM Listings WHERE price BETWEEN :minPrice AND :maxPrice"
		    		+ " AND area BETWEEN :minArea AND :maxArea "
		    		+ " AND room_type_id LIKE :roomType "
		    		+ "ORDER BY price ASC",
		    nativeQuery = true
		)
		List<Listings> findListingsByLAT(
		    @Param("minPrice") BigDecimal minPrice,
		    @Param("maxPrice") BigDecimal maxPrice,
		    @Param("minArea") BigDecimal minArea,
		    @Param("maxArea") BigDecimal maxArea,
		    @Param("roomType") String roomType
		);


}


