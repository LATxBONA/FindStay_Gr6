package com.example.PHONGTROSPRING.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.PHONGTROSPRING.entities.PhongTro;
import com.example.PHONGTROSPRING.entities.ThongTinNguoiThue;
import com.example.PHONGTROSPRING.repository.ThongTinNguoiThueRepository;

@Service
public class ThongTinNguoiThueService {
    
    @Autowired
    private ThongTinNguoiThueRepository nguoiThueRepository;
    
    @Autowired
    private PhongTroService phongTroService;
    
    public boolean isCCCDExists(String soCCCD) {
        return nguoiThueRepository.findBySoCCCD(soCCCD).isPresent();
    }
    
    public boolean isSDTExists(String sdt) {
        return nguoiThueRepository.findBySDT(sdt).isPresent();
    }
    
    public List<ThongTinNguoiThue> getAllNguoiThue() {
        return nguoiThueRepository.findAll();
    }
    
    public ThongTinNguoiThue getNguoiThueById(int id) {
        return nguoiThueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người thuê"));
    }
    
    public List<ThongTinNguoiThue> getNguoiThueByPhongTro(int phongTroId) {
        return nguoiThueRepository.findByPhongTro_Id(phongTroId);
    }
    
    @Transactional
    public void deleteNguoiThue(int id) {
        ThongTinNguoiThue nguoiThue = getNguoiThueById(id);
        PhongTro phongTro = nguoiThue.getPhongTro();
        nguoiThueRepository.deleteById(id);
        
        // Cập nhật trạng thái phòng nếu không còn ai thuê
        List<ThongTinNguoiThue> tenants = getNguoiThueByPhongTro(phongTro.getId());
        if(tenants.isEmpty()) {
            phongTro.setStatus("Trống");
            phongTroService.updatePhongTro(phongTro.getId(), phongTro);
        }
    }
    
    @Transactional
    public ThongTinNguoiThue updateNguoiThue(int id, ThongTinNguoiThue nguoiThueUpdate) {
        ThongTinNguoiThue existingNguoiThue = getNguoiThueById(id);
        
        // Kiểm tra CCCD mới có bị trùng không (nếu thay đổi)
        if(!existingNguoiThue.getSoCCCD().equals(nguoiThueUpdate.getSoCCCD()) &&
           isCCCDExists(nguoiThueUpdate.getSoCCCD())) {
            throw new RuntimeException("Số CCCD đã tồn tại trong hệ thống");
        }
        
        // Kiểm tra SĐT mới có bị trùng không (nếu thay đổi)
        if(!existingNguoiThue.getsDT().equals(nguoiThueUpdate.getsDT()) &&
           isSDTExists(nguoiThueUpdate.getsDT())) {
            throw new RuntimeException("Số điện thoại đã tồn tại trong hệ thống");
        }
        
        existingNguoiThue.setHoTen(nguoiThueUpdate.getHoTen());
        existingNguoiThue.setSoCCCD(nguoiThueUpdate.getSoCCCD());
        existingNguoiThue.setsDT(nguoiThueUpdate.getsDT());
        
        return nguoiThueRepository.save(existingNguoiThue);
    }
    
    @Transactional
    public ThongTinNguoiThue saveNguoiThue(ThongTinNguoiThue nguoiThue, int phongTroId) {
        // Kiểm tra CCCD đã tồn tại
        if(isCCCDExists(nguoiThue.getSoCCCD())) {
            throw new RuntimeException("Số CCCD đã tồn tại trong hệ thống");
        }
        
        // Kiểm tra SĐT đã tồn tại
        if(isSDTExists(nguoiThue.getsDT())) {
            throw new RuntimeException("Số điện thoại đã tồn tại trong hệ thống");
        }
        
        // Gán phòng trọ cho người thuê
        PhongTro phongTro = phongTroService.getPhongTroById(phongTroId);
        nguoiThue.setPhongTro(phongTro);
        
        // Cập nhật trạng thái phòng
        phongTro.setStatus("Có người");
        phongTroService.updatePhongTro(phongTroId, phongTro);
        
        return nguoiThueRepository.save(nguoiThue);
    }
}