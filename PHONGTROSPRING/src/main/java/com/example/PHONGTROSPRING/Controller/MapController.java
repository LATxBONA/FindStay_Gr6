package com.example.PHONGTROSPRING.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bando")
public class MapController {

	@GetMapping()
	public String map() {
		return "views/map";
	}
}
