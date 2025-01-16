package com.example.PHONGTROSPRING.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.PhongTro;

@Repository
public interface PhongTroRepository extends JpaRepository<PhongTro, Integer> {
	// Tìm phòng theo listing ID
    List<PhongTro> findByListings_itemId(int itemId);
    
    // Tìm phòng theo tên và listing ID để check trùng
    Optional<PhongTro> findByTenPhongAndListings_itemId(String tenPhong, int itemId);
    
    // Query tìm phòng trống
    @Query("SELECT p FROM PhongTro p WHERE p.thongTinNguoiThue IS EMPTY")
    List<PhongTro> findAvailableRooms();
    
    // Tìm phòng theo ID và listing ID
    Optional<PhongTro> findByIdAndListings_itemId(int id, int itemId);
    
    List<PhongTro> findByListings(Listings listings);
 
}