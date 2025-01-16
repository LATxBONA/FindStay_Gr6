package com.example.PHONGTROSPRING.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.PhongTro;
import com.example.PHONGTROSPRING.repository.PhongTroRepository;

@Service
public class PhongTroService {
    
    @Autowired
    private PhongTroRepository phongTroRepository;
    
    public List<PhongTro> getAllPhongTro() {
        return phongTroRepository.findAll();
    }
    public List<PhongTro> getAllPhongTroById(int id){
    	return phongTroRepository.findByListings_itemId(id);
    }
    public PhongTro getPhongTroById(int id) {
        return phongTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng trọ"));
    }
    
    public List<PhongTro> getPhongTroByListingId(int itemId) {
        return phongTroRepository.findByListings_itemId(itemId); // Sửa tên method gọi
    }
    
    public List<PhongTro> getAvailableRooms() {
        return phongTroRepository.findAvailableRooms();
    }
    
    public PhongTro savePhongTro(PhongTro phongTro) {
        // Kiểm tra null
        if (phongTro.getListings() == null || phongTro.getTenPhong() == null) {
            throw new RuntimeException("Thông tin không đầy đủ");
        }

        Optional<PhongTro> existingRoom = phongTroRepository.findByTenPhongAndListings_itemId(
            phongTro.getTenPhong(),
            phongTro.getListings().getItemId()
        );

        // Khi thêm mới, phongTro.getId() sẽ là 0
        if (existingRoom.isPresent()) {
            throw new RuntimeException("Tên phòng đã tồn tại trong listing này");
        }

        return phongTroRepository.save(phongTro);
    }
    public void deletePhongTro(int id) {
        PhongTro phongTro = getPhongTroById(id);
        if(!phongTro.getThongTinNguoiThue().isEmpty()) { // Sửa kiểm tra List rỗng
            throw new RuntimeException("Không thể xóa phòng đang có người thuê");
        }
        phongTroRepository.deleteById(id);
    }
    
    public PhongTro updatePhongTro(int id, PhongTro phongTroUpdate) {
        PhongTro existingPhong = getPhongTroById(id);
        existingPhong.setTenPhong(phongTroUpdate.getTenPhong());
        // Cập nhật các thông tin khác...
        return phongTroRepository.save(existingPhong);
    }
}