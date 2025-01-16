package com.example.PHONGTROSPRING.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PHONGTROSPRING.service.ImagesService;

@RestController
public class ImagesController {

	@Autowired
	private ImagesService imagesService;

	/*
	 * @GetMapping("/add-images") public String add() { imagesService.add(); return
	 * "ok"; }
	 */
}
