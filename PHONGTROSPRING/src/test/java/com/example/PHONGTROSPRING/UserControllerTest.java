package com.example.PHONGTROSPRING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.PHONGTROSPRING.Controller.UserController;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.service.UserService;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        session = new MockHttpSession();
    }

    @Test
    void testChangeInfo_Success() {
        String fullName = "Nguyễn Văn A";
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setUserId("123");
        mockUser.setFullName("Old Name");
        mockUser.setEmail("old@example.com");
        
        session.setAttribute("user", mockUser);
        when(userService.updateUser(any(User.class))).thenReturn(true);
        when(userService.getUser("123")).thenReturn(mockUser);

        String result = userController.changeInfo(redirectAttributes, fullName, email, session, model);

        assertEquals("redirect:/info", result);
        verify(redirectAttributes).addFlashAttribute("status", "Cập nhật thành công");
        verify(userService).updateUser(any(User.class));
    }

    @Test
    void testChangeInfo_Failure() {

        String fullName = "Nguyễn Văn A";
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setUserId("123");
        
        session.setAttribute("user", mockUser);
        when(userService.updateUser(any(User.class))).thenReturn(false);

        String result = userController.changeInfo(redirectAttributes, fullName, email, session, model);

        assertEquals("redirect:/info", result);
        verify(model).addAttribute("status", "Có lỗi xảy ra");
    }

    @Test
    void testChangePass_Success() {
        String currentPassword = "oldpass";
        String newPassword = "newpass";
        User mockUser = new User();
        mockUser.setUserId("123");
        session.setAttribute("user", mockUser);
        when(userService.changePass(mockUser, currentPassword, newPassword)).thenReturn(true);
        String result = userController.changPass(redirectAttributes, currentPassword, newPassword, session, model);
        assertEquals("redirect:/info", result);
        verify(redirectAttributes).addFlashAttribute("status", "Đổi mật khẩu thành công");
    }

    @Test
    void testChangePass_Failure() {
        String currentPassword = "wrongpass";
        String newPassword = "newpass";
        User mockUser = new User();
        mockUser.setUserId("123");
        session.setAttribute("user", mockUser);
        when(userService.changePass(mockUser, currentPassword, newPassword)).thenReturn(false);
        String result = userController.changPass(redirectAttributes, currentPassword, newPassword, session, model);
        assertEquals("redirect:/info", result);
        verify(redirectAttributes).addFlashAttribute("status", "Đổi mật khẩu thất bại");
    }
}