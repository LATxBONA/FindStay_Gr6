package com.example.PHONGTROSPRING.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "RoomTypes")
public class RoomTypes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roomTypeId;

	@Column(length = 100, columnDefinition = "varchar(255)")
	private String roomTypeName;

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}
	
	public void setRoomTypeName(String roomTypeName) {
		String roomTypeTemp = "Phòng trọ";

		if (roomTypeName.equals("Nhà nguyên căn")) {
			roomTypeTemp = "Nhà nguyên căn";
		} else if (roomTypeName.equals("Căn hộ")) {
			roomTypeTemp = "Căn hộ";
		}else if (roomTypeName.equals("Mặt bằng")) {
			roomTypeTemp = "Mặt bằng";
		}

		this.roomTypeName = roomTypeTemp;
	}
}
