package com.example.PHONGTROSPRING.repository;

import org.springframework.data.domain.*;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.response.ListingsResponse;

@Repository
public interface ListingsRepository extends JpaRepository<Listings, Integer> {

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
}
