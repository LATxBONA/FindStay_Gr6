package com.example.PHONGTROSPRING.Controller;

import com.example.PHONGTROSPRING.entities.BlogPost;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.response.phongtroresponse;
import com.example.PHONGTROSPRING.service.BlogPostService;
import com.example.PHONGTROSPRING.service.ListingsService;
import com.example.PHONGTROSPRING.service.ServicePostNew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class BlogPostController {
	@Autowired
	private ListingsService listingsService;

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private ServicePostNew ServicePostNew;

    @GetMapping("/blog")
    public String showBlogPage(Model model) {
        List<BlogPost> blogPosts = blogPostService.getAllPosts();
        List<Listings> listings = listingsService.getAllListings(); 
        model.addAttribute("blogPosts", blogPosts);
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
			/* phongtro.setLocation(listings.get(i).getLocation()); */
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
        model.addAttribute("listings", listphongtrocoanh);
        return "views/blog"; // Trỏ đến file blog.html trong thư mục templates
    }
}