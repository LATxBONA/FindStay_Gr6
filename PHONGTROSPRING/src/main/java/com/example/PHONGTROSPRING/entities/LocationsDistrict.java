package com.example.PHONGTROSPRING.entities;

import jakarta.persistence.*;

@Entity
public class LocationsDistrict {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int district_id;

	@ManyToOne
	@JoinColumn(name = "city_id", nullable = false, referencedColumnName = "city_id")
	private LocationsCity location_city;

	@Column(nullable = false)
	private String district;

	public int getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(int district_id) {
		this.district_id = district_id;
	}

	public LocationsCity getLocation_city() {
		return location_city;
	}

	public void setLocation_city(LocationsCity location_city) {
		this.location_city = location_city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
}
