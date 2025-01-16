package com.example.PHONGTROSPRING;

import com.example.PHONGTROSPRING.entities.Images;
import com.example.PHONGTROSPRING.entities.Listings;
import com.example.PHONGTROSPRING.entities.ListingsFeatures;
import com.example.PHONGTROSPRING.entities.User;
import com.example.PHONGTROSPRING.repository.ImagesRepository;
import com.example.PHONGTROSPRING.repository.ListingsFeaturesRepository;
import com.example.PHONGTROSPRING.repository.ListingsRepository;
import com.example.PHONGTROSPRING.repository.PaymentHistoryRepository;
import com.example.PHONGTROSPRING.response.ListingsResponse;
import com.example.PHONGTROSPRING.service.ListingsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DetailListingTest {
	@InjectMocks
    private ListingsService listingsService;

    @Mock
    private ListingsRepository listingsRepository;

    @Mock
    private ImagesRepository imagesRepository;

    @Mock
    private ListingsFeaturesRepository listingsFeaturesRepository;

    @Mock
    private PaymentHistoryRepository paymentHistoryRepository;

    private Listings mockListing;
    private Images mockImage;
    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Tạo đối tượng giả lập cho bài viết
        mockListing = new Listings();
        mockListing.setItemId(1);
        mockListing.setTitle("Room for Rent");
        mockListing.setCreatedAt(null); // Thêm ngày tạo nếu cần

        // Tạo đối tượng giả lập cho hình ảnh
        mockImage = new Images();
        mockImage.setImageId(1);
        mockImage.setImageUrl(new byte[0]);

        // Tạo đối tượng người dùng giả lập
        mockUser = new User();
        mockUser.setUserId("user123");
    }

    @Test
    void testGetAllListings() {
        // Mock phương thức findAll
        List<Listings> listings = new ArrayList<>();
        listings.add(mockListing);
        when(listingsRepository.findAll()).thenReturn(listings);

        // Gọi phương thức
        List<Listings> result = listingsService.getAllListings();

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockListing, result.get(0));
    }

    @Test
    void testGetListingByUser() {
        // Mock phương thức findByUser
        Pageable pageable = PageRequest.of(0, 5);
        Page<Listings> listingsPage = mock(Page.class);
        when(listingsRepository.findByUser(any(User.class), eq(pageable))).thenReturn(listingsPage);

        // Gọi phương thức
        Page<Listings> result = listingsService.getListingByUser(mockUser, pageable);

        // Kiểm tra kết quả
        assertNotNull(result);
        verify(listingsRepository, times(1)).findByUser(any(User.class), eq(pageable));
    }

    @Test
    void testGetListingsFeatures() {
        // Mock phương thức findListingsFeatures
        ListingsFeatures mockFeatures = new ListingsFeatures();
        when(listingsRepository.findListingsFeatures(1)).thenReturn(mockFeatures);

        // Gọi phương thức
        ListingsFeatures result = listingsService.getListingsFeatures(1);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(mockFeatures, result);
    }

    @Test
    void testGetImages() {
        // Mock phương thức findByItemId
        List<byte[]> imageBytesList = new ArrayList<>();
        imageBytesList.add(new byte[0]);
        when(imagesRepository.findByItemId(1)).thenReturn(imageBytesList);

        // Gọi phương thức
        List<String> result = listingsService.getImages(1);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAllListingsFeatured() {
        // Mock phương thức findAllListingsFeatured
        List<ListingsResponse> mockResponse = new ArrayList<>();
        when(listingsRepository.findAllListingsFeatured(anyInt(), anyInt(), anyInt(), any(Pageable.class)))
                .thenReturn(mockResponse);

        // Gọi phương thức
        List<ListingsResponse> result = listingsService.findAllListingsFeatured(1, 1, 1);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(0, result.size()); // Do đã mock là danh sách rỗng
    }

    @Test
    void testEncryptionImages() {
        // Giả lập dữ liệu hình ảnh
        List<byte[]> imageBytesList = new ArrayList<>();
        imageBytesList.add(new byte[]{1, 2, 3});
        List<String> encryptedImages = listingsService.encryptionImages(imageBytesList);

        // Kiểm tra kết quả
        assertNotNull(encryptedImages);
        assertEquals(1, encryptedImages.size());
    }

    @Test
    void testGetQuantityPost() {
        // Mock phương thức getQuantityPost
        when(listingsRepository.getQuantityPost()).thenReturn(10);

        // Gọi phương thức
        int result = listingsService.getQuantityPost();

        // Kiểm tra kết quả
        assertEquals(10, result);
    }

    @Test
    void testFindById() {
        // Mock phương thức findById
        when(listingsRepository.findById(1)).thenReturn(Optional.of(mockListing));

        // Gọi phương thức
        Listings result = listingsService.findById(1);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(mockListing, result);
    }
}
