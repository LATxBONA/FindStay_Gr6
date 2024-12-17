package com.example.PHONGTROSPRING.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.User;

@Repository
public interface ListingsRepository extends JpaRepository<Listings, Integer>, JpaSpecificationExecutor<Listings>{

	@Query("SELECT l FROM Listings l WHERE l.location.locationId = :locationId ORDER BY l.postType DESC, l.createdAt DESC")
	List<Listings> findAllListingsOrderByPostTypeAndCreatedAt(@Param("locationId") int locationId);
	
	@Query("SELECT l FROM Listings l WHERE l.location.locationId = :locationId ORDER BY l.createdAt DESC")
	List<Listings> findAllListingsFollowLocationAndCreatedAt(@Param("locationId") int locationId);


	Page<Listings> findByStatusOrPostTypeOrTitleAndUser(String status, int postType, String title, User user, Pageable pageable);
	
	Page<Listings> findByUser(User user,Pageable pageable);
=======
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


