package com.example.PHONGTROSPRING.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PHONGTROSPRING.service.RoomTypesService;

@RestController
public class RoomTypesController {

	@Autowired
	 private RoomTypesService roomTypesService;
	
	@GetMapping("/add-rooms")
    public String addMultipleRoomTypes() {

       // roomTypesService.addMultipleRoomTypes(); 

		/* roomTypesService.addMultipleRoomTypes(); */ 

        return "Multiple Room Types added successfully!";
    }
}
