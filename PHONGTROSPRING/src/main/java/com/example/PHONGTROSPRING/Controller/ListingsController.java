package com.example.PHONGTROSPRING.Controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.PHONGTROSPRING.entities.Images;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.response.phongtroresponse;
import com.example.PHONGTROSPRING.service.ImagesService;
import com.example.PHONGTROSPRING.service.ListingsService;
import com.example.PHONGTROSPRING.service.ServicePostNew;

@Controller
public class ListingsController {

	@Autowired
	private ListingsService listingsService;

	@Autowired
	private ImagesService imageService;
	
	@Autowired
	private ServicePostNew ServicePostNew;

	/*
	 * @GetMapping("/add-listing") public String add() {
	 * listingsService.addSampleListings(); return "ok"; }
	 */

	@GetMapping("/detailRoom/id={roomId}")
	public String detailRoom(@PathVariable int roomId, Model model) {

		// Tin chính
		model.addAttribute("listDescription",
				listingsService.cutStringDescription(listingsService.getRoomById(roomId).getDescription()));
		model.addAttribute("room", listingsService.getRoomById(roomId));
		model.addAttribute("image", imageService.getImageById(roomId));
		model.addAttribute("time", listingsService.date(listingsService.getRoomById(roomId).getCreatedAt()));

		// Tin nổi bật
		model.addAttribute("FeaturedNew",
				listingsService.getRoomFeatured(listingsService.getRoomById(roomId).getLocation().getLocationId()));
		model.addAttribute("imgFeaturedNew", listingsService.getImageFollowRoomFeatured(imageService.getImage()));
		model.addAttribute("timeFeaturedNew", listingsService.dateArray(
				listingsService.getRoomFeatured(listingsService.getRoomById(roomId).getLocation().getLocationId())));

		// Tin mới đăng
		model.addAttribute("newRooms",
				listingsService.getNewRoom(listingsService.getRoomById(roomId).getLocation().getLocationId()));
		model.addAttribute("timeNewRooms", listingsService.dateArray(
				listingsService.getNewRoom(listingsService.getRoomById(roomId).getLocation().getLocationId())));

		return "views/detailRooms";
	}

	@GetMapping("/phongtro")
	public String PhongtroInfo(Model model) {
	
		// Lấy danh sách phòng từ service
		List<Listings> listings = listingsService.getAllListings();
		List<phongtroresponse> listphongtrocoanh = new ArrayList<phongtroresponse>();
		for(int i=0;i<listings.size();i++) {
			List<byte[]> imageBytes = ServicePostNew.getanh(listings.get(i).getItemId());

			List<String> listurlimg = new ArrayList<String>();
			for(byte[] img : imageBytes) {
				listurlimg.add("data:image/jpg;base64,"+ Base64.getEncoder().encodeToString(img));
			}
			
			
			phongtroresponse phongtro = new phongtroresponse();
			phongtro.setListings(listurlimg);
			phongtro.setItemId(listings.get(i).getItemId());
			phongtro.setUser(listings.get(i).getUser());
			phongtro.setTitle(listings.get(i).getTitle());
			phongtro.setDescription(listings.get(i).getDescription());
			phongtro.setPrice(listings.get(i).getPrice());
			phongtro.setArea(listings.get(i).getArea());
			phongtro.setLocation(listings.get(i).getLocation());
			phongtro.setAddress(listings.get(i).getAddress());
			phongtro.setRoomType(listings.get(i).getRoomType());
			phongtro.setCreatedAt(listings.get(i).getCreatedAt());
			phongtro.setExpiryDate(listings.get(i).getExpiryDate());
			phongtro.setUpdatedAt(listings.get(i).getUpdatedAt());
			phongtro.setPostType(listings.get(i).getPostType());
			phongtro.setStatus(listings.get(i).getStatus());
			phongtro.setObject(listings.get(i).getObject());
			
			
			
			listphongtrocoanh.add(phongtro);
			
		}
		
		//String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
		/*
		 * model.addAttribute("urlimg", listurlimg);
		 * System.out.println("anh ne "+listurlimg);
		 */
		// Đưa danh sách vào model để truyền qua HTML
		model.addAttribute("listings", listphongtrocoanh);
		//System.out.println("data co hoac khong " + listphongtrocoanh);

		return "views/phongtro";
	}

	/*
	 * @GetMapping("/phongtro") public String getanh(@PathVariable int id, Model
	 * model) { List<byte[]> imageBytes = ServicePostNew.getanh(id); List<String>
	 * listurlimg = new ArrayList<String>(); for(byte[] img : imageBytes) {
	 * listurlimg.add("data:image/png;base64,"+
	 * Base64.getEncoder().encodeToString(img)); } //String base64Image =
	 * "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
	 * model.addAttribute("urlimg", listurlimg); return "views/phongtro"; }
	 */

}
