package com.example.PHONGTROSPRING.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.PaymentHistory;
import com.example.PHONGTROSPRING.entities.User;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Integer> {
	Page<PaymentHistory> findByUser(User user, Pageable pageable);
	List<PaymentHistory> findByListings(Listings listings);
}
