package com.example.PHONGTROSPRING.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.ListingsFeatures;

@Repository
public interface ListingsFeaturesRepository extends JpaRepository<ListingsFeatures, Integer> {

}
