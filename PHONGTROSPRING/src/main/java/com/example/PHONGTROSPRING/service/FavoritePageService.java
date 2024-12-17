package com.example.PHONGTROSPRING.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.repository.FavoritePageRepository;
import com.example.PHONGTROSPRING.repository.ListingsRepository;
import com.example.PHONGTROSPRING.response.ListingsResponse;

@Service
public class FavoritePageService {

	@Autowired
	private FavoritePageRepository favoriteRepository;
	
	@Autowired
	private ListingsRepository listingsRepository;
	
	public List<Integer> findByUserId(String user_id){
		return favoriteRepository.findByUserId(user_id);
	}
	
	public ListingsResponse getListingsByItemId(int item_id) {
		return listingsRepository.getListingsByItemId(item_id);
	}
	
	public void deleteFavorite(String user_id, int item_id) {
		favoriteRepository.deleteFavorite(user_id, item_id);
	}
}
