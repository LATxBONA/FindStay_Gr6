package com.example.PHONGTROSPRING;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.PHONGTROSPRING.entities.Transactions;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.repository.TransactionsRepository;
import com.example.PHONGTROSPRING.repository.UserRepository;
import com.example.PHONGTROSPRING.request.RechargeRequest;
import com.example.PHONGTROSPRING.service.RechargeService;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class RechargeServiceTest {
    @InjectMocks
    private RechargeService rechargeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionsRepository transactionsRepository;

    private User testUser;
    private RechargeRequest testRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setBalance(BigDecimal.valueOf(100000));
        testRequest = new RechargeRequest();
    }

    @Test
    void testCalculateBonus_LowTier() {
        testRequest.setAmount(BigDecimal.valueOf(50000));
        BigDecimal bonus = rechargeService.calculateBonus(testRequest.getAmount());
        assertEquals(0, bonus.compareTo(new BigDecimal("5000.00")),
                "10% bonus for amount < 100,000");
    }

    @Test
    void testCalculateBonus_MidTier() {
        testRequest.setAmount(BigDecimal.valueOf(1000000));
        BigDecimal bonus = rechargeService.calculateBonus(testRequest.getAmount());
        assertEquals(0, bonus.compareTo(new BigDecimal("200000.00")),
                "20% bonus for amount >= 1,000,000");
    }

    @Test
    void testCalculateBonus_HighTier() {
        testRequest.setAmount(BigDecimal.valueOf(3000000));
        BigDecimal bonus = rechargeService.calculateBonus(testRequest.getAmount());
        assertEquals(0, bonus.compareTo(new BigDecimal("750000.00")),
                "25% bonus for amount >= 3,000,000");
    }

    @Test
    void testRecharge_SuccessfulTransaction() {
        testRequest.setAmount(BigDecimal.valueOf(50000));
        HttpSession session = mockSession(testUser);

        when(userRepository.save(any(User.class))).thenReturn(testUser);
        rechargeService.createRechargeRequest(testRequest, session);

        assertEquals(0, testUser.getBalance().compareTo(new BigDecimal("155000.00")),
                "Balance should be initial (100,000) + amount (50,000) + bonus (5,000)");
        verify(userRepository).save(testUser);
        verify(transactionsRepository).save(any(Transactions.class));
    }

    @Test
    void testRecharge_WithZeroAmount() {
        testRequest.setAmount(BigDecimal.ZERO);
        HttpSession session = mockSession(testUser);

        assertThrows(IllegalArgumentException.class,
                () -> rechargeService.createRechargeRequest(testRequest, session),
                "Should throw exception for zero amount");
    }

    @Test
    void testRecharge_WithNegativeAmount() {
        testRequest.setAmount(BigDecimal.valueOf(-50000));
        HttpSession session = mockSession(testUser);

        assertThrows(IllegalArgumentException.class,
                () -> rechargeService.createRechargeRequest(testRequest, session),
                "Should throw exception for negative amount");
    }

    private HttpSession mockSession(User user) {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);
        return session;
    }
}
