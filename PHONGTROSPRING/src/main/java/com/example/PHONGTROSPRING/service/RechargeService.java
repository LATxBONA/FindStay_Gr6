package com.example.PHONGTROSPRING.service;

import com.example.PHONGTROSPRING.entities.Transactions;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.repository.TransactionsRepository;
import com.example.PHONGTROSPRING.repository.UserRepository;
import com.example.PHONGTROSPRING.request.RechargeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Service
public class RechargeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    
	private BigDecimal calculateBonus(BigDecimal amount) {
	    if (amount.compareTo(BigDecimal.valueOf(50000)) >= 0 && amount.compareTo(BigDecimal.valueOf(1000000)) < 0) {
	        return amount.multiply(BigDecimal.valueOf(0.1)); // Tặng 10%
	    } else if (amount.compareTo(BigDecimal.valueOf(1000000)) >= 0 && amount.compareTo(BigDecimal.valueOf(2000000)) < 0) {
	        return amount.multiply(BigDecimal.valueOf(0.2)); // Tặng 20%
	    } else if (amount.compareTo(BigDecimal.valueOf(2000000)) >= 0) {
	        return amount.multiply(BigDecimal.valueOf(0.25)); // Tặng 25%
	    } else {
	        return BigDecimal.ZERO; // Không áp dụng ưu đãi
	    }
	}
	
    public void recharge(RechargeRequest request, HttpSession session) {
        // Lấy thông tin người dùng từ session
        User user = (User) session.getAttribute("user");
        
		BigDecimal bonus = calculateBonus(request.getAmount());
        // Cập nhật số dư
        BigDecimal newBalance = user.getBalance().add(request.getAmount()).add(bonus);
        user.setBalance(newBalance);
        userRepository.save(user);
        
        

        // Tạo giao dịch
        Transactions transaction = new Transactions();
        transaction.setUser(user);
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate();
        transaction.setStatus("Đang xử lý");
        transactionsRepository.save(transaction);    
        
    }
    
    public List<Transactions> getRechargeHistory(HttpSession session) {
    	User user = (User)session.getAttribute("user");
    	return transactionsRepository.findByUser(user);
    }
    
}