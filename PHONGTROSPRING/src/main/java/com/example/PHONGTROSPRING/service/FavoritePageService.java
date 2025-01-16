package com.example.PHONGTROSPRING.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.Favorites;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.repository.FavoritePageRepository;
import com.example.PHONGTROSPRING.repository.ListingsRepository;
import com.example.PHONGTROSPRING.response.ListingsResponse;

@Service
public class FavoritePageService {

	@Autowired
	private FavoritePageRepository favoriteRepository;

	@Autowired
	private ListingsRepository listingsRepository;

	public List<Integer> findByUserId(String user_id) {
		return favoriteRepository.findByUserId(user_id);
	}

	public ListingsResponse getListingsByItemId(int item_id) {
		return listingsRepository.getListingsByItemId(item_id);
	}

	public void deleteFavorite(String user_id, int item_id) {
		favoriteRepository.deleteFavorite(user_id, item_id);
	}

	public Favorites addFavorite(User user, int itemId) {
		Favorites favorite = favoriteRepository.checkFavoriteExist(user.getUserId(), itemId);
		if(favorite == null) {
			Optional<Listings> listing = listingsRepository.findById(itemId);
			
			Favorites favo = new Favorites();
			favo.setListing(listing.get());
			favo.setUser(user);
			return favoriteRepository.save(favo);
		}
		
		deleteFavorite(user.getUserId(), itemId);
		return favorite;
	}
}
