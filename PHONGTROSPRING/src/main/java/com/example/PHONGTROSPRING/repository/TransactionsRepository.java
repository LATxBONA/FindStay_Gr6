package com.example.PHONGTROSPRING.repository;

import com.example.PHONGTROSPRING.entities.Transactions;
import com.example.PHONGTROSPRING.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

	List<Transactions> findByUser(User user);
	
	List<Transactions> findByUser_UserId(String userId);

	@Query("SELECT t FROM Transactions t " + "WHERE (:status IS NULL OR t.status = :status)")
	List<Transactions> seachTransactions(String status);
}
