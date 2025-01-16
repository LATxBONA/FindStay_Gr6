package com.example.PHONGTROSPRING.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.Favorites;
import com.example.PHONGTROSPRING.entities.Listings;

@Repository
public interface FavoritePageRepository extends JpaRepository<Favorites, Integer> {

	@Query("SELECT f.listing.itemId FROM Favorites f WHERE f.user.userId = :user_id")
	List<Integer> findByUserId(@Param("user_id") String user_id);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Favorites f WHERE f.listing = :listing")
	void deleteFavoritebylisting(@Param("listing") Listings Listings);
	
	Favorites findByListing(Listings listing);

	@Modifying
	@Transactional
	@Query("DELETE FROM Favorites f WHERE f.user.userId = :user_id AND f.listing.itemId = :item_id")
	void deleteFavorite(@Param("user_id") String user_id, @Param("item_id") int item_id);
	
	@Query("SELECT f FROM Favorites f WHERE f.user.userId = :user_id AND f.listing.itemId = :item_id")
	Favorites checkFavoriteExist(@Param("user_id") String user_id, @Param("item_id") int item_id);
}
