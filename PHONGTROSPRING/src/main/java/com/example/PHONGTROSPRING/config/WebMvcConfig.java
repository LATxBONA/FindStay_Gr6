package com.example.PHONGTROSPRING.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Interceptor cho trang admin
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**"); // Áp dụng cho tất cả các URL bắt đầu bằng /admin/

        // Interceptor cho trang đăng tin
        registry.addInterceptor(new PostListingInterceptor())
                .addPathPatterns("/dangtin/**", "/dangtin"); // Chặn cả /dangtin và các URL con của nó
    }
}
