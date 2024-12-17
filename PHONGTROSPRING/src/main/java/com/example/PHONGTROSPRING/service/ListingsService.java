package com.example.PHONGTROSPRING.service;

import org.springframework.data.domain.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.LocationsDistrict;
import com.example.PHONGTROSPRING.repository.ImagesRepository;
import com.example.PHONGTROSPRING.repository.ListingsRepository;
import com.example.PHONGTROSPRING.repository.LocationsDistrictRepository;
import com.example.PHONGTROSPRING.response.ListingsResponse;

@Service
public class ListingsService {

	@Autowired
	private ListingsRepository listingRepository;

	@Autowired
	private ImagesRepository imagesRepository;

	private Pageable pageable_5 = PageRequest.of(0, 5);
	private Pageable pageable_1 = PageRequest.of(0, 1);

	// Lấy tất cả danh sách room để render lên view
	public List<Listings> getAllListings() {
		List<Listings> list = new ArrayList<>();

		list = listingRepository.findAll();

		return list;
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

	public int getQuantityPost() {
		return listingRepository.getQuantityPost();
	}

	public Page<ListingsResponse> getListings(int roomtype_id, int city_id, int page, int size) {
		return listingRepository.getListings(roomtype_id, city_id, PageRequest.of(page, size));
	}

	public Page<ListingsResponse> getListingsNationWide(int roomtype_id, int page, int size) {
		return listingRepository.getListingsNationWide(roomtype_id, PageRequest.of(page, size));
	}
}
