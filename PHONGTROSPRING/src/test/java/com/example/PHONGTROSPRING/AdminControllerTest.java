package com.example.PHONGTROSPRING;

import com.example.PHONGTROSPRING.Controller.AdminController;
import com.example.PHONGTROSPRING.service.AdminService;
import com.example.PHONGTROSPRING.service.ListingsService;
import com.example.PHONGTROSPRING.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import com.example.PHONGTROSPRING.entities.User;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdminService adminService;

    @Mock
    private UserService userService;

    @Mock
    private ListingsService listingsService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void testViewAdmin() throws Exception {
        mockMvc.perform(get("/admin/"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/admin/layout/layoutAdmin"));
    }

    @Test
    void testViewTransactions() throws Exception {
        mockMvc.perform(get("/admin/transactions"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/admin/layout/layoutAdmin"))
                .andExpect(model().attributeExists("transactions"))
                .andExpect(model().attributeExists("contentTemplate"));
    }

    @Test
    void testUpdateTransactions() throws Exception {
        mockMvc.perform(post("/admin/transactions/update")
                .param("transactionId", "1")
                .param("newStatus", "Completed"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/transactions"));
    }

    @Test
    void testFilterTransactions() throws Exception {
        mockMvc.perform(get("/admin/transactions/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/admin/layout/layoutAdmin"))
                .andExpect(model().attributeExists("transactions"))
                .andExpect(model().attributeExists("transactionsRequest"))
                .andExpect(model().attributeExists("contentTemplate"));
    }

    @Test
    void testViewUsers() throws Exception {
        mockMvc.perform(get("/admin/accounts"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/admin/layout/layoutAdmin"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("contentTemplate"));
    }

    @Test
    void testFilterAccount() throws Exception {
        mockMvc.perform(get("/admin/accounts/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/admin/layout/layoutAdmin"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("accountRequest"))
                .andExpect(model().attributeExists("contentTemplate"));
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/admin/accounts/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/admin/layout/layoutAdmin"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("contentTemplate"));
    }

    @Test
    void testSaveAccount() throws Exception {
        mockMvc.perform(post("/admin/accounts/save")
                .param("username", "testUser")
                .param("password", "testPass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/accounts"));
    }

    @Test
    void testShowEditForm() throws Exception {
        User mockUser = Mockito.mock(User.class);
        when(userService.findById("1")).thenReturn(Optional.of(mockUser));

        mockMvc.perform(get("/admin/accounts/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/admin/layout/layoutAdmin"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("contentTemplate"));
    }

    @Test
    void testDeleteAccount() throws Exception {
        mockMvc.perform(post("/admin/accounts/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/accounts"));
    }

    @Test
    void testViewListing() throws Exception {
        mockMvc.perform(get("/admin/listings"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/admin/layout/layoutAdmin"))
                .andExpect(model().attributeExists("listings"))
                .andExpect(model().attributeExists("contentTemplate"));
    }

    @Test
    void testFilterListing() throws Exception {
        mockMvc.perform(get("/admin/listings/search")
                .param("status", "active"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/admin/layout/layoutAdmin"))
                .andExpect(model().attributeExists("listings"))
                .andExpect(model().attributeExists("contentTemplate"));
    }

    @Test
    void testUpdateListing() throws Exception {
        mockMvc.perform(post("/admin/listings/update")
                .param("itemId", "1")
                .param("newStatus", "active"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/listings"));
    }
}
