package com.example.PHONGTROSPRING;

import com.example.PHONGTROSPRING.entities.Favorites;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.repository.FavoritePageRepository;
import com.example.PHONGTROSPRING.repository.ListingsRepository;
import com.example.PHONGTROSPRING.service.FavoritePageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.anyInt;


public class FavoritePageTest {
	@InjectMocks
    private FavoritePageService favoritePageService;

    @Mock
    private FavoritePageRepository favoriteRepository;

    @Mock
    private ListingsRepository listingsRepository;

    private User mockUser;
    private Listings mockListing;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Khởi tạo các đối tượng mock
        mockUser = new User();
        mockUser.setUserId("user123");

        mockListing = new Listings();
        mockListing.setItemId(1);
        mockListing.setTitle("Room for Rent");
    }

    @Test
    void testAddFavorite_NewFavorite() {
        // Giả sử bài chưa có trong danh sách yêu thích
        when(favoriteRepository.checkFavoriteExist(anyString(), anyInt())).thenReturn(null);
        when(listingsRepository.findById(anyInt())).thenReturn(Optional.of(mockListing));
        when(favoriteRepository.save(any(Favorites.class))).thenReturn(new Favorites());

        // Thực hiện thêm bài yêu thích
        Favorites result = favoritePageService.addFavorite(mockUser, 1);

        // Kiểm tra kết quả
        assertNotNull(result, "Favorite should be added.");
        verify(favoriteRepository, times(1)).save(any(Favorites.class));  // Kiểm tra rằng phương thức save đã được gọi một lần
    }

    @Test
    void testDeleteFavorite() {
        // Giả sử bài yêu thích cần xóa
        doNothing().when(favoriteRepository).deleteFavorite(anyString(), anyInt());

        // Thực hiện xóa bài yêu thích
        favoritePageService.deleteFavorite("user123", 1);

        // Kiểm tra rằng phương thức deleteFavorite đã được gọi một lần
        verify(favoriteRepository, times(1)).deleteFavorite("user123", 1);
    }

    @Test
    void testFindByUserId() {
        // Giả sử chúng ta đã mock phương thức findByUserId
        when(favoriteRepository.findByUserId(anyString())).thenReturn(List.of(1, 2, 3));

        // Thực hiện tìm kiếm danh sách yêu thích của người dùng
        var result = favoritePageService.findByUserId("user123");

        // Kiểm tra rằng kết quả trả về là danh sách yêu thích của người dùng
        assertNotNull(result, "Favorite list should not be null");
        assertEquals(3, result.size(), "User should have 3 favorite items");
    }
}
