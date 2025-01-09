package com.example.PHONGTROSPRING;

import com.example.PHONGTROSPRING.service.ListingsService;
import com.example.PHONGTROSPRING.Controller.ListingsController;
import com.example.PHONGTROSPRING.response.ListingsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SearchTest {

    private MockMvc mockMvc;

    @Mock
    private ListingsService listingsService;

    @Mock
    private Model model;

    @InjectMocks
    private ListingsController listingsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(listingsController).build();
    }

    @Test
    void testSearchByCriteria_Success() throws Exception {
        // 1. Thiết lập dữ liệu test
        BigDecimal minPrice = BigDecimal.valueOf(0);
        BigDecimal maxPrice = BigDecimal.valueOf(999999999);
        BigDecimal minArea = BigDecimal.valueOf(0);
        BigDecimal maxArea = BigDecimal.valueOf(999999999);
        Integer roomType = 1;
        Integer cityId = 1;
        Integer districtId = 1;
        Integer wardId = 1;
        int page = 0;

        // 2. Tạo dữ liệu giả lập cho response
        ListingsResponse mockListing = new ListingsResponse(
                1,                              // itemId
                "Phòng Test",                   // title
                BigDecimal.valueOf(1000000),    // price
                LocalDateTime.now(),            // createdAt
                "Phòng trọ",                    // roomType
                "Hồ Chí Minh",                  // location_city
                "Quận 1",                       // location_district
                "Phường Bến Nghé",              // location_ward
                "123 Đường ABC",                // address
                "Nguyễn Văn A",                 // user_name
                "0123456789",                   // phone
                1,                              // postType
                BigDecimal.valueOf(20)          // area
            );
        mockListing.setItemId(1);
        mockListing.setTitle("Phòng Test");
        mockListing.setPrice(BigDecimal.valueOf(1000000));
        mockListing.setArea(BigDecimal.valueOf(20));

        List<ListingsResponse> listingsResponses = Arrays.asList(mockListing);
        Page<ListingsResponse> mockPage = new PageImpl<>(
                listingsResponses,
                PageRequest.of(0, 10),
                1
        );

        // 3. Thiết lập hành vi mock cho service
        when(listingsService.getListingsByLAT(
                any(BigDecimal.class),
                any(BigDecimal.class),
                any(BigDecimal.class),
                any(BigDecimal.class),
                any(Integer.class),
                any(Integer.class),
                any(Integer.class),
                any(Integer.class),
                any(PageRequest.class)
        )).thenReturn(mockPage);

        // 4. Mock cho phương thức findImageByItemId
        when(listingsService.findImageByItemId(anyInt()))
                .thenReturn(Arrays.asList("mockImageUrl"));

        // 5. Thực hiện request và kiểm tra kết quả
        mockMvc.perform(get("/search")
                        .param("minPrice", minPrice.toString())
                        .param("maxPrice", maxPrice.toString())
                        .param("minArea", minArea.toString())
                        .param("maxArea", maxArea.toString())
                        .param("roomType", roomType.toString())
                        .param("city_id", cityId.toString())
                        .param("district_id", districtId.toString())
                        .param("ward_id", wardId.toString())
                        .param("page", String.valueOf(page)))
                .andExpect(status().isOk())
                .andExpect(view().name("views/kq_search"))
                .andExpect(model().attributeExists("list_room"))
                .andExpect(model().attributeExists("totalPage"))
                .andExpect(model().attributeExists("prePage"))
                .andExpect(model().attributeExists("nextPage"))
                .andExpect(model().attributeExists("isFirstPage"))
                .andExpect(model().attributeExists("isLastPage"));

        // 6. Verify service được gọi
        verify(listingsService).getListingsByLAT(
                eq(minPrice),
                eq(maxPrice),
                eq(minArea),
                eq(maxArea),
                eq(roomType),
                eq(cityId),
                eq(districtId),
                eq(wardId),
                any(PageRequest.class)
        );
    }

    @Test
    void testSearchByCriteria_DefaultParams() throws Exception {
        // 1. Tạo Page rỗng cho response mặc định
        Page<ListingsResponse> emptyPage = new PageImpl<>(
                Collections.emptyList(),
                PageRequest.of(0, 10),
                0
        );

        // 2. Mock service response cho tham số mặc định
        when(listingsService.getListingsByLAT(
                any(BigDecimal.class),
                any(BigDecimal.class),
                any(BigDecimal.class),
                any(BigDecimal.class),
                any(),
                any(),
                any(),
                any(),
                any(PageRequest.class)
        )).thenReturn(emptyPage);

        // 3. Thực hiện request với tham số mặc định
        mockMvc.perform(get("/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/kq_search"))
                .andExpect(model().attributeExists("list_room"))
                .andExpect(model().attributeExists("totalPage"))
                .andExpect(model().attributeExists("prePage"))
                .andExpect(model().attributeExists("nextPage"))
                .andExpect(model().attributeExists("isFirstPage"))
                .andExpect(model().attributeExists("isLastPage"));
    }

    @Test
    void testSearchByCriteria_InvalidPage() throws Exception {
        // 1. Tạo dữ liệu test với page < 0
        Page<ListingsResponse> emptyPage = new PageImpl<>(
                Collections.emptyList(),
                PageRequest.of(0, 10),
                0
        );

        // 2. Mock service response
        when(listingsService.getListingsByLAT(
                any(BigDecimal.class),
                any(BigDecimal.class),
                any(BigDecimal.class),
                any(BigDecimal.class),
                any(),
                any(),
                any(),
                any(),
                any(PageRequest.class)
        )).thenReturn(emptyPage);

        // 3. Thực hiện request với page âm
        mockMvc.perform(get("/search")
                        .param("page", "-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/kq_search"))
                .andExpect(model().attributeExists("list_room"))
                .andExpect(model().attributeExists("totalPage"))
                .andExpect(model().attribute("page", 0)); // Kiểm tra page được set về 0
    }

    @Test
    void testSearchByCriteria_LastPage() throws Exception {
        // 1. Tạo dữ liệu test cho trang cuối
        ListingsResponse mockListing = new ListingsResponse(
                1,                              // itemId
                "Phòng Test",                   // title
                BigDecimal.valueOf(1000000),    // price
                LocalDateTime.now(),            // createdAt
                "Phòng trọ",                    // roomType
                "Hồ Chí Minh",                  // location_city
                "Quận 1",                       // location_district
                "Phường Bến Nghé",              // location_ward
                "123 Đường ABC",                // address
                "Nguyễn Văn A",                 // user_name
                "0123456789",                   // phone
                1,                              // postType
                BigDecimal.valueOf(20)          // area
            );
        mockListing.setItemId(1);
        mockListing.setTitle("Phòng Test");

        List<ListingsResponse> listingsResponses = Arrays.asList(mockListing);
        Page<ListingsResponse> lastPage = new PageImpl<>(
                listingsResponses,
                PageRequest.of(2, 10), // page 2
                21 // total elements
        );

        // 2. Mock service response
        when(listingsService.getListingsByLAT(
                any(BigDecimal.class),
                any(BigDecimal.class),
                any(BigDecimal.class),
                any(BigDecimal.class),
                any(),
                any(),
                any(),
                any(),
                any(PageRequest.class)
        )).thenReturn(lastPage);

        when(listingsService.findImageByItemId(anyInt()))
                .thenReturn(Arrays.asList("mockImageUrl"));

        // 3. Thực hiện request
        mockMvc.perform(get("/search")
                        .param("page", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/kq_search"))
                .andExpect(model().attributeExists("list_room"))
                .andExpect(model().attribute("isLastPage", true));
    }
}