package com.example.PHONGTROSPRING;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.example.PHONGTROSPRING.Controller.RechargeController;
import com.example.PHONGTROSPRING.service.RechargeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
class RechargeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RechargeService rechargeService;

    @InjectMocks
    private RechargeController rechargeController;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(rechargeController).build();
    }

    @Test
    void testRechargePage_UserNotLoggedIn() throws Exception {
        mockMvc.perform(get("/recharge"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testRechargePage_UserLoggedIn() throws Exception {
        mockMvc.perform(get("/recharge")
                .sessionAttr("user", "testUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/recharge"));
    }

    @Test
    void testRecharge_InvalidAmount() throws Exception {
        mockMvc.perform(post("/recharge3")
                .sessionAttr("user", "testUser")
                .param("amount", "-100"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/recharge2"))
                .andExpect(model().attributeExists("rechargefail"));
    }

    @Test
    void testRecharge_ValidAmount() throws Exception {
        mockMvc.perform(post("/recharge3")
                .sessionAttr("user", "testUser")
                .param("amount", "500000"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/recharge3"))
                .andExpect(model().attributeExists("amount"))
                .andExpect(result -> {
                    Object actualValue = result.getModelAndView().getModel().get("amount");
                    System.out.println("Actual type: " + actualValue.getClass());
                    System.out.println("Actual value: " + actualValue);
                });
    }
}
