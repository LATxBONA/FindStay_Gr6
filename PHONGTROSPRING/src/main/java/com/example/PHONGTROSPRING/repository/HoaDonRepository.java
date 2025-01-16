package com.example.PHONGTROSPRING.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.HoaDon;
import com.example.PHONGTROSPRING.entities.PhongTro;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer>{
	Optional<HoaDon> findByPhongTroId(int phongId);
	List<HoaDon> findByPhongTro(PhongTro phongtro);
}
