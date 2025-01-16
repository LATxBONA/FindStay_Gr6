package com.example.PHONGTROSPRING.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.Images;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.repository.ImagesRepository;

@Service
public class ImagesService {

	@Autowired
	private ImagesRepository imagesReopsitory;
	
	public List<Images> getImageById(int listingId){
		return imagesReopsitory.findByListingItemId(listingId);
	}
	
	public List<Images> getImage(){
		return imagesReopsitory.findAll(Sort.by(Sort.Order.asc("listing.itemId")));
	}
}
