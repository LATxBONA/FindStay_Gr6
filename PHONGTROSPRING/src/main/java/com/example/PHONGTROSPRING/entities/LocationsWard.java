package com.example.PHONGTROSPRING.entities;

import jakarta.persistence.*;

@Entity
public class LocationsWard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ward_id;

	@ManyToOne
	@JoinColumn(name = "district_id", nullable = false, referencedColumnName = "district_id")
	private LocationsDistrict location_district;

	@Column(nullable = false)
	private String ward;

	public int getWard_id() {
		return ward_id;
	}

	public void setWard_id(int ward_id) {
		this.ward_id = ward_id;
	}

	public LocationsDistrict getLocation_district() {
		return location_district;
	}

	public void setLocation_district(LocationsDistrict location_district) {
		this.location_district = location_district;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}
}
