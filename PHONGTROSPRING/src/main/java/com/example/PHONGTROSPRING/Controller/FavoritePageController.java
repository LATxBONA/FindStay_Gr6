package com.example.PHONGTROSPRING.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.PHONGTROSPRING.entities.Favorites;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.response.ListingsResponse;
import com.example.PHONGTROSPRING.service.FavoritePageService;
import com.example.PHONGTROSPRING.service.ListingsService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FavoritePageController {

	@Autowired
	private FavoritePageService favoritePageService;

	@Autowired
	private ListingsService listingsService;

	@GetMapping("/favorite")
	public String redirectFavoritepage(@RequestParam(value = "id") String user_id, Model model) {

		List<Integer> list_favorite = favoritePageService.findByUserId(user_id);
		List<ListingsResponse> list_response = new ArrayList<>();

		for (Integer id : list_favorite) {
			list_response.add(favoritePageService.getListingsByItemId(id));
		}

		model.addAttribute("list_room", setImageForListingsResponse(list_response));

		return "views/favoritePage";
	}

	// set ảnh cho từng đối tượng listingsResponse
	public List<ListingsResponse> setImageForListingsResponse(List<ListingsResponse> listingResponse) {
		List<ListingsResponse> list = new ArrayList<>();

		for (ListingsResponse item : listingResponse) {
			if (listingsService.findImageByItemId(item.getItemId()).size() > 0) {
				item.setImageUrl(listingsService.findImageByItemId(item.getItemId()).get(0));
			}
			list.add(item);
		}

		return list;
	}

	@GetMapping("/delete_favorite/{item_id}")
	public ResponseEntity<Void> deleteFavorite(@PathVariable int item_id, HttpSession session) {

		User user = (User) session.getAttribute("user");

		// Kiểm tra nếu người dùng đã đăng nhập
		if (user != null) {
			favoritePageService.deleteFavorite(user.getUserId(), item_id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/add_favorite/{itemId}")
	public ResponseEntity<Favorites> addFavorite(@PathVariable int itemId, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<>(favoritePageService.addFavorite(user, itemId), HttpStatus.OK);
	}

}
