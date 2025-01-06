package com.example.PHONGTROSPRING.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.PaymentHistory;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.repository.PaymentHistoryRepository;

@Service
public class PaymentHistoryService {

	@Autowired
	private PaymentHistoryRepository paymentHistoryRepository;
	
	public Page<PaymentHistory> getPaymentHistory(User user, Pageable pageable) {
		return paymentHistoryRepository.findByUser(user, pageable);
	}
	
}
