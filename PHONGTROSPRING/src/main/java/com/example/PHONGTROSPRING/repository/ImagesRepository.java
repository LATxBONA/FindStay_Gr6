package com.example.PHONGTROSPRING.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.Images;
import com.example.PHONGTROSPRING.entities.Listings;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Integer>{
	

	List<Images> findByListingItemId(int itemId);
	List<Images> findByListing(Listings listing);
}
