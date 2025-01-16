package com.example.PHONGTROSPRING.service;

import com.example.PHONGTROSPRING.entities.BlogPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.example.PHONGTROSPRING.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

    @Autowired
    private BlogRepository blogPostRepository;

    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }
    
    public BlogPost getPostById(Integer id) {
        return blogPostRepository.findById(id).orElse(null);
    }
    
    public List<BlogPost> getRecentBlogPosts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);  // Lấy 3 bài viết mới nhất
        Page<BlogPost> page = blogPostRepository.findTopNRecentPosts(pageable);
        return page.getContent();  // Lấy danh sách các bài viết từ Page
    }



}
