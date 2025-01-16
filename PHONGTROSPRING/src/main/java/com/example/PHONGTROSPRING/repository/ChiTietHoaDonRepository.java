package com.example.PHONGTROSPRING.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.ChiTietHoaDon;
import com.example.PHONGTROSPRING.entities.HoaDon;

@Repository
public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon, Integer> {
	List<ChiTietHoaDon> getAllByHoaDonId(int id);

	void deleteByHoaDonId(Integer hoaDonId);
}
