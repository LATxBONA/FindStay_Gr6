
//package com.example.PHONGTROSPRING.Controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.example.PHONGTROSPRING.entities.User;
//import com.example.PHONGTROSPRING.request.RequestPostNew;
//import com.example.PHONGTROSPRING.request.RequestThanhToan;
//import com.example.PHONGTROSPRING.service.LocationService;
//import com.example.PHONGTROSPRING.service.RoomTypesService;
//import com.example.PHONGTROSPRING.service.ServicePostNew;
//
//import jakarta.servlet.http.HttpSession;
//import jakarta.websocket.Session;
//
//@Controller
//public class dangTinController {
//
//	@Autowired
//	private LocationService LocationService;
//	@Autowired
//	private RoomTypesService RoomTypesService;
//	@Autowired
//	private ServicePostNew ServicePostNew;
//
//	@GetMapping("/dangtin")
//	public String dangtin(Model model) {
//		model.addAttribute("locations", LocationService.getAllLocation());
//		model.addAttribute("roomtypes", RoomTypesService.getAllRoomTypes());
//		return "views/dangtin";
//	}
//
//	@GetMapping("/city")
//	public String city(@RequestParam("selectedOption") String request, Model model) {
//
//		model.addAttribute("locations", LocationService.getAllLocation());
////		model.addAttribute("locationlist", LocationService.getAllLocations(request));
//		model.addAttribute("roomtypes", RoomTypesService.getAllRoomTypes());
//		model.addAttribute("selectedcity", request);
//
//		return "views/dangtin";
//	}
//
//	@PostMapping("/dangtin")
//	public String dtbdangtin(@ModelAttribute RequestPostNew request, @ModelAttribute RequestThanhToan requesttt,
//			Model model, HttpSession session) {
//		// ServicePostNew.postNew(request);
//		// System.out.println(request.getUrlAnh());
//
//		User user = (User) session.getAttribute("user");
//		request.setUserid(user.getUserId());
//		ServicePostNew.postNew(request, requesttt);
//		// session.setAttribute("requestpost", request);
//		// redirectAttributes.addFlashAttribute("requestpost", request);
//		// model.addAttribute("requestthanhtoan", request);
//
//		return "views/dangtin";
//	}
//
//	/*
//	 * @GetMapping("/getanh/{id}") public String getanh(@PathVariable String id,
//	 * Model model) { byte[] imageBytes = ServicePostNew.getanh(id); String
//	 * base64Image = "data:image/png;base64," +
//	 * Base64.getEncoder().encodeToString(imageBytes);
//	 * model.addAttribute("base64Image", base64Image); return "views/html";
//	 * 
//	 * }
//	 */
//
//	/*
//	 * @GetMapping("/thanhtoantin") public String thanhtoantin() { return
//	 * "views/thanhtoantin"; }
//	 */
//	/*
//	 * @PostMapping("/successdangtin") public String sucessdangtin(@ModelAttribute
//	 * RequestThanhToan requesttt, HttpSession session) {
//	 * 
//	 * RequestPostNew requestpn = (RequestPostNew)
//	 * session.getAttribute("requestpost");
//	 * //System.out.println("tin tin tin tin tin tin tin"+requestpn.getUrlAnh());
//	 * ServicePostNew.postNew(requestpn, requesttt);
//	 * 
//	 * return "views/dangtin"; }
//	 */
//
//	/*
//	 * @GetMapping("/dataget") public String
//	 * getthoigian(@RequestParam("goithoigian") String goithoigian, Model model) {
//	 * List<Integer> time = new ArrayList<>();
//	 * 
//	 * String bientime = "";
//	 * 
//	 * if (goithoigian.equals("ngay")) { for (int i = 1; i <= 60; i++) {
//	 * time.add(i); } bientime = "ngày"; } else if (goithoigian.equals("tuan")) {
//	 * for (int i = 1; i <= 30; i++) { time.add(i); } bientime = "tuần"; } else if
//	 * (goithoigian.equals("thang")) { for (int i = 1; i <= 12; i++) { time.add(i);
//	 * } bientime = "tháng"; }
//	 * 
//	 * model.addAttribute("thoigian", time); model.addAttribute("bientime",
//	 * bientime);
//	 * 
//	 * return "views/dangtin"; }
//	 */
//
//}

package com.example.PHONGTROSPRING.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.PHONGTROSPRING.entities.*;
import com.example.PHONGTROSPRING.request.*;
import com.example.PHONGTROSPRING.service.*;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class dangTinController {

	/*
	 * @Autowired private LocationService LocationService;
	 */
	@Autowired
	private RoomTypesService RoomTypesService;
	@Autowired
	private ServicePostNew ServicePostNew;
	@Autowired
	private ListingsService ListingsService;
	@Autowired
	private LocationsCityService locationsCityService;
	@Autowired
	private LocationsDistrictService locationsDistrictService;
	@Autowired
	private LocationsWardService LocationsWardService;

	@GetMapping("/dangtin")
	public String dangtin(Model model) {
		model.addAttribute("locationscity", locationsCityService.getAllCity());
		model.addAttribute("roomtypes", RoomTypesService.getAllRoomTypes());
		return "views/dangtin";
	}

	@GetMapping("/dangtin/{city_id}")
	@ResponseBody
	public List<LocationsDistrict> city(@PathVariable("city_id") int city_id) {
		return locationsDistrictService.getDistrict(city_id);
	}

	@GetMapping("/dangtin/city/{districtId}")
	@ResponseBody
	public List<LocationsWard> ward(@PathVariable("districtId") int districtId) {

		LocationsDistrict district = locationsDistrictService.get1District(districtId); // Tìm đối tượng district từ ID
		return LocationsWardService.getAllWard(district);
	}

	@PostMapping("/dangtin")
	public String dtbdangtin(RedirectAttributes redirectattributes ,@ModelAttribute RequestPostNew request, @ModelAttribute RequestThanhToan requesttt, @ModelAttribute ListingsFeatures listingsfeatures,
			Model model, HttpSession session) {

		User user = (User) session.getAttribute("user");
		request.setUser(user);
		
		if(ServicePostNew.postNew(request, requesttt, listingsfeatures)) {
			return "redirect:/quanlytin";
		}else {
			redirectattributes.addFlashAttribute("msg", "Không đủ tiền để đăng tin");
			return "redirect:/dangtin";
		}

		
	}

	/*
	 * @GetMapping("/getanh/{id}") public String getanh(@PathVariable String id,
	 * Model model) { byte[] imageBytes = ServicePostNew.getanh(id); String
	 * base64Image = "data:image/png;base64," +
	 * Base64.getEncoder().encodeToString(imageBytes);
	 * model.addAttribute("base64Image", base64Image); return "views/html";
	 * 
	 * }
	 */

	/*
	 * @GetMapping("/thanhtoantin") public String thanhtoantin() { return
	 * "views/thanhtoantin"; }
	 */
	/*
	 * @PostMapping("/successdangtin") public String sucessdangtin(@ModelAttribute
	 * RequestThanhToan requesttt, HttpSession session) {
	 * 
	 * RequestPostNew requestpn = (RequestPostNew)
	 * session.getAttribute("requestpost");
	 * //System.out.println("tin tin tin tin tin tin tin"+requestpn.getUrlAnh());
	 * ServicePostNew.postNew(requestpn, requesttt);
	 * 
	 * return "views/dangtin"; }
	 */

	@GetMapping("/dataget/{goithoigian}")
	@ResponseBody
	public List<RequestTime> getthoigian(@PathVariable("goithoigian") String goithoigian) {
		List<RequestTime> time = new ArrayList<>();

		String bientime = "";

		if (goithoigian.equals("ngay")) {
			for (int i = 1; i <= 60; i++) {
				RequestTime rqtime = new RequestTime("Ngày", i);
				time.add(rqtime);
			}
			bientime = "ngày";
		} else if (goithoigian.equals("tuan")) {
			for (int i = 1; i <= 30; i++) {
				RequestTime rqtime = new RequestTime("Tuần", i);
				time.add(rqtime);
			}
			bientime = "tuần";
		} else if (goithoigian.equals("thang")) {
			for (int i = 1; i <= 12; i++) {
				RequestTime rqtime = new RequestTime("Tháng", i);
				time.add(rqtime);
			}
			bientime = "tháng";
		}
		return time;

	}


}
