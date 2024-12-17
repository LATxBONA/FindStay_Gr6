//package com.example.PHONGTROSPRING.Controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.PHONGTROSPRING.entities.Locations;
//import com.example.PHONGTROSPRING.request.RequestThanhToan;
//import com.example.PHONGTROSPRING.service.LocationService;
//
//@RestController
//public class LocationController {
//
//	@Autowired
//	private LocationService locationService;
//
//	@GetMapping("/add-locations")
//	public String addLocations() {
//		locationService.addLocations(); // Gọi service để thêm dữ liệu
//		return "Locations added successfully!";
//	}
//	
//	@GetMapping("/getcity")
//	public List<Locations> getcity(@RequestParam("selectedvalue") String request){
//		return locationService.getAllLocations(request);
//	}
//	
//	@GetMapping("/getdataday")
//	public List<String> getthoigian(@RequestParam("goithoigian") String goithoigian) {
//		List<String> time = new ArrayList<>();
//
//		String bientime = "";
//
//		if (goithoigian.equals("ngay")) {
//			for (int i = 1; i <= 60; i++) {
//				time.add("ngày");
//			}
//			//bientime = "ngày";
//		} else if (goithoigian.equals("tuan")) {
//			for (int i = 1; i <= 30; i++) {
//				time.add("tuần");
//			}
//			//bientime = "tuần";
//		} else if (goithoigian.equals("thang")) {
//			for (int i = 1; i <= 12; i++) {
//				time.add("tháng");
//			}
//			//bientime = "tháng";
//		}
//
//		return time;
//	} 
//}
