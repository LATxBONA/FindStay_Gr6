package com.example.PHONGTROSPRING.entities;

import jakarta.persistence.*;

@Entity
public class ListingsFeatures {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "item_id", nullable = true, referencedColumnName = "itemId")
	private Listings listings;

	@Column(nullable = true)
	private Boolean fullFurniture;	//đầy đủ nội thất

	@Column(nullable = true)
	private Boolean washingMachine;	//máy giặt

	@Column(nullable = true)
	private Boolean freeTime;	//thgian tư do

	@Column(nullable = true)
	private Boolean attic;	//gác

	@Column(nullable = true)
	private Boolean refrigerator;	//tủ lạnh

	@Column(nullable = true)
	private Boolean protection;	//bảo vệ 24/24

	@Column(nullable = true)
	private Boolean kitchenShelves;	//kệ bếp

	@Column(nullable = true)
	private Boolean elevator;	//thang máy

	@Column(nullable = true)
	private Boolean parkingBasement;	//hầm để xe

	@Column(nullable = true)
	private Boolean airConditioning;	//điều hòa

	@Column(nullable = true)
	private Boolean commonOwner;	//chung chủ

	public ListingsFeatures() {
	        this.fullFurniture = false;
	        this.washingMachine = false;
	        this.freeTime = false;
	        this.attic = false;
	        this.refrigerator = false;
	        this.protection = false;
	        this.kitchenShelves = false;
	        this.elevator = false;
	        this.parkingBasement = false;
	        this.airConditioning = false;
	        this.commonOwner = false;
	    }
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Listings getListings() {
		return listings;
	}

	public void setListings(Listings listings) {
		this.listings = listings;
	}

	public Boolean getFullFurniture() {
		return fullFurniture;
	}

	public void setFullFurniture(Boolean fullFurniture) {
		this.fullFurniture = fullFurniture;
	}

	public Boolean getWashingMachine() {
		return washingMachine;
	}

	public void setWashingMachine(Boolean washingMachine) {
		this.washingMachine = washingMachine;
	}

	public Boolean getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(Boolean freeTime) {
		this.freeTime = freeTime;
	}

	public Boolean getAttic() {
		return attic;
	}

	public void setAttic(Boolean attic) {
		this.attic = attic;
	}

	public Boolean getRefrigerator() {
		return refrigerator;
	}

	public void setRefrigerator(Boolean refrigerator) {
		this.refrigerator = refrigerator;
	}

	public Boolean getProtection() {
		return protection;
	}

	public void setProtection(Boolean protection) {
		this.protection = protection;
	}

	public Boolean getKitchenShelves() {
		return kitchenShelves;
	}

	public void setKitchenShelves(Boolean kitchenShelves) {
		this.kitchenShelves = kitchenShelves;
	}

	public Boolean getElevator() {
		return elevator;
	}

	public void setElevator(Boolean elevator) {
		this.elevator = elevator;
	}

	public Boolean getParkingBasement() {
		return parkingBasement;
	}

	public void setParkingBasement(Boolean parkingBasement) {
		this.parkingBasement = parkingBasement;
	}

	public Boolean getAirConditioning() {
		return airConditioning;
	}

	public void setAirConditioning(Boolean airConditioning) {
		this.airConditioning = airConditioning;
	}

	public Boolean getCommonOwner() {
		return commonOwner;
	}

	public void setCommonOwner(Boolean commonOwner) {
		this.commonOwner = commonOwner;
	}
}
