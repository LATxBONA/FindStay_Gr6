package com.example.PHONGTROSPRING.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.RoomTypes;

@Repository
public interface RoomTypesRepository extends JpaRepository<RoomTypes, Integer> {
   
	@Query("SELECT r FROM RoomTypes r WHERE r.roomTypeName = :roomTypeName")
	Optional<RoomTypes> searchRoomTypes(@Param("roomTypeName") String roomTypeName);
	
	@Query("SELECT r.roomTypeId FROM RoomTypes r WHERE r.roomTypeName = :roomTypeName")
	int findRoomTypeByName(@Param("roomTypeName") String roomTypeName);
}
