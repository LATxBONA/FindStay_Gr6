package com.example.PHONGTROSPRING.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PHONGTROSPRING.entities.Transactions;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.repository.TransactionsRepository;
import com.example.PHONGTROSPRING.repository.UserRepository;
import com.example.PHONGTROSPRING.request.TransactionsRequest;

@Service
public class AdminService {

	@Autowired
	private TransactionsRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;

	public List<Transactions> viewTransactions() {
		return transactionRepository.findAll();
	}
	
public void updateTransactions(Integer transactionId, String newStatus) {
		
		Transactions transaction = transactionRepository.findById(transactionId).orElse(null);
		
		if("Thành công".equals(newStatus) && "Đang xử lý".equals(transaction.getStatus())) {
			
			BigDecimal bonus = RechargeService.calculateBonus(transaction.getAmount());
			User user = transaction.getUser();
	        BigDecimal newBalance = user.getBalance().add(transaction.getAmount()).add(bonus);
	        user.setBalance(newBalance);
	        userRepository.save(user);
		}
		
		transaction.setStatus(newStatus);
		transactionRepository.save(transaction);

	}
	public List<Transactions> searchTransactions(TransactionsRequest transactionsRequest) {
	    String status = transactionsRequest.getStatus();	    
	    return transactionRepository.seachTransactions(status);
	}
	
	public void deleteTransactionsByUserId(String userId) {
	    List<Transactions> transactions = transactionRepository.findByUser_UserId(userId);
	    transactionRepository.deleteAll(transactions);
	}
}
