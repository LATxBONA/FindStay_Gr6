package com.example.PHONGTROSPRING.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.*;

@Repository
public interface LocationsCityRepository extends JpaRepository<LocationsCity, Integer>{
//	List<LocationsCity> findByCity(String city);
	
	@Query("SELECT c FROM LocationsCity c WHERE c.city = :city")
	Optional<LocationsCity> findByCity(@Param("city") String city);
	
	@Query("SELECT c.city_id FROM LocationsCity c WHERE c.city = :city")
	int findByCityName(@Param("city") String city);
}
