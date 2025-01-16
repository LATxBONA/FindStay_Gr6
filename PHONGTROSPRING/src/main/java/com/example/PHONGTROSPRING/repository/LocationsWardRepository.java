package com.example.PHONGTROSPRING.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.PHONGTROSPRING.entities.LocationsDistrict;
import com.example.PHONGTROSPRING.entities.LocationsWard;

public interface LocationsWardRepository extends JpaRepository<LocationsWard, Integer> {

	@Query("SELECT w FROM LocationsWard w WHERE w.ward = :ward")
	Optional<LocationsWard> findByWard(@Param("ward") String ward);
	
	List<LocationsWard> findByLocationDistrict(LocationsDistrict locationDistrict);
}
