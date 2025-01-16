package com.example.PHONGTROSPRING.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.PHONGTROSPRING.entities.ThongTinNguoiThue;
import com.example.PHONGTROSPRING.service.ThongTinNguoiThueService;

@Controller
public class NguoiThueController {
    @Autowired
    private ThongTinNguoiThueService nguoiThueService;
    
    @PostMapping("/nguoi-thue/check-cccd")
    @ResponseBody
    public boolean checkCCCD(String soCCCD) {
        return nguoiThueService.isCCCDExists(soCCCD);
    }
    
    @PostMapping("/nguoi-thue/check-sdt")
    @ResponseBody
    public boolean checkSDT(String sdt) {
        return nguoiThueService.isSDTExists(sdt);
    }
    
    @PostMapping("/nguoi-thue/{id}/edit")
    public String editTenant(@PathVariable Integer id, 
                           @ModelAttribute ThongTinNguoiThue nguoiThue,
                           RedirectAttributes redirectAttributes) {
        Integer phongId = nguoiThueService.getNguoiThueById(id).getPhongTro().getId();
        try {
            nguoiThueService.updateNguoiThue(id, nguoiThue);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin người thuê thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể cập nhật: " + e.getMessage());
        }
        return "redirect:/phong/" + phongId + "/chi-tiet";
    }
    
    @PostMapping("/nguoi-thue/{id}/delete")
    public String deleteTenant(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        ThongTinNguoiThue nguoiThue = nguoiThueService.getNguoiThueById(id);
        Integer phongId = nguoiThue.getPhongTro().getId();
        
        try {
            nguoiThueService.deleteNguoiThue(id);
            redirectAttributes.addFlashAttribute("message", "Xóa người thuê thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa người thuê: " + e.getMessage());
        }
        return "redirect:/phong/" + phongId + "/chi-tiet";
    }
}