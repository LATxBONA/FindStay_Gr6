package com.example.PHONGTROSPRING.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.RoomTypes;
import com.example.PHONGTROSPRING.repository.RoomTypesRepository;

@Service
public class RoomTypesService {

	@Autowired
	private RoomTypesRepository roomTypesRepository;
	

	/*
	 * public void addRoomType(String roomTypeName) { RoomTypes roomType = new
	 * RoomTypes(); roomType.setRoomTypeName(roomTypeName);
	 * roomTypesRepository.save(roomType); }
	 * 
	 * public void addMultipleRoomTypes() { addRoomType("Phòng trọ");
	 * addRoomType("Nhà nguyên căn"); addRoomType("Căn hộ");
	 * addRoomType("Mặt bằng"); }
	 */

    public List<RoomTypes> getAllRoomTypes(){
    	return roomTypesRepository.findAll();
    }
    
    public int findRoomTypeByName(String roomName) {
    	return roomTypesRepository.findRoomTypeByName(roomName);
    }
}
