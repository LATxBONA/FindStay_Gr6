package com.example.PHONGTROSPRING.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.PhongTro;
import com.example.PHONGTROSPRING.entities.ThongTinNguoiThue;
import com.example.PHONGTROSPRING.service.PhongTroService;
import com.example.PHONGTROSPRING.service.ThongTinNguoiThueService;

@Controller
public class PhongController {

    @Autowired
    private PhongTroService phongTroService;
    
    @Autowired
    private ThongTinNguoiThueService nguoiThueService;
    
    @GetMapping("/phong/list")
    public String listRooms(Model model) {
        List<PhongTro> rooms = phongTroService.getAllPhongTro();
        model.addAttribute("rooms", rooms);
     // Thêm đối tượng mới cho modal thêm phòng
        model.addAttribute("newRoom", new PhongTro());
        return "views/danh-sach-phong";
    }

    
    @GetMapping("/phong/{id}")
    public String getById(Model model , @PathVariable int id) {
        List<PhongTro> rooms = phongTroService.getAllPhongTroById(id);
        model.addAttribute("rooms", rooms);
     // Thêm đối tượng mới cho modal thêm phòng
        model.addAttribute("newRoom", new PhongTro());
        model.addAttribute("listingId", id);
        return "views/danh-sach-phong";
    }
    
    
    @PostMapping("/phong/add/{id}")
    public String addRoom(@ModelAttribute PhongTro phongTro, RedirectAttributes redirectAttributes, @PathVariable int id) {
        try {
            // Tạo và set listings
            Listings listing = new Listings();
            listing.setItemId(id);
            phongTro.setListings(listing);
            
//            phongTro.setStatus("Trống"); 
            phongTroService.savePhongTro(phongTro);
            redirectAttributes.addFlashAttribute("message", "Thêm phòng mới thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể thêm phòng: " + "đã tồn tại");
        }
        return "redirect:/phong/" + id;
    }
    
    @GetMapping("/phong/{id}/chi-tiet")
    public String showRoomDetail(@PathVariable Integer id, Model model) {
        PhongTro room = phongTroService.getPhongTroById(id);
        List<ThongTinNguoiThue> tenants = nguoiThueService.getNguoiThueByPhongTro(id);
        
        // Cập nhật trạng thái phòng dựa trên số người thuê
        room.setStatus(tenants.isEmpty() ? "Trống" : "Có người");
        phongTroService.updatePhongTro(id, room);
        
        model.addAttribute("room", room);
        model.addAttribute("tenants", tenants);
        model.addAttribute("newTenant", new ThongTinNguoiThue());
        return "views/chi-tiet-phong";
    }
    @PostMapping("/phong/{id}/edit")
    public String editRoom(@PathVariable Integer id, 
                          @ModelAttribute PhongTro phongTro,
                          RedirectAttributes redirectAttributes) {
        try {
            phongTroService.updatePhongTro(id, phongTro);
            redirectAttributes.addFlashAttribute("message", "Cập nhật phòng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể cập nhật phòng: " + e.getMessage());
        }
        return "redirect:/phong/list";
    }
    @PostMapping("/phong/{id}/delete")
    public String deleteRoom(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            phongTroService.deletePhongTro(id);
            redirectAttributes.addFlashAttribute("message", "Xóa phòng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa phòng: " + e.getMessage());
        }
        return "redirect:/phong/list";
    }
    
    
    
    @PostMapping("/phong/{id}/them-nguoi-thue")
    public String addTenant(@PathVariable Integer id,
                          @ModelAttribute ThongTinNguoiThue nguoiThue,
                          RedirectAttributes redirectAttributes) {
        try {
            // Thực hiện thêm người thuê
            nguoiThueService.saveNguoiThue(nguoiThue, id);
            
            // Cập nhật trạng thái phòng
            PhongTro room = phongTroService.getPhongTroById(id);
            room.setStatus("Có người");
            phongTroService.updatePhongTro(id, room);
            
            redirectAttributes.addFlashAttribute("message", "Thêm người thuê thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể thêm người thuê: " + e.getMessage());
        }
        return "redirect:/phong/" + id + "/chi-tiet";
    }
}