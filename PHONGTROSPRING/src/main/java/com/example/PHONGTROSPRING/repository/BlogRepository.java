package com.example.PHONGTROSPRING.repository;

import com.example.PHONGTROSPRING.entities.BlogPost;
import com.example.PHONGTROSPRING.response.blogpostresponse;
import org.springframework.data.domain.Page;       
import org.springframework.data.domain.Pageable; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<BlogPost, Integer> {

    @Query("SELECT new com.example.PHONGTROSPRING.response.blogpostresponse("
            + "b.postId, b.user, b.title, b.message, b.createdAt) "
            + "FROM BlogPost b ORDER BY b.createdAt DESC")
    Page<blogpostresponse> getAllBlogPostsByNewest(Pageable pageable);  
    
    @Query("SELECT b FROM BlogPost b ORDER BY b.createdAt DESC")
    Page<BlogPost> findTopNRecentPosts(Pageable pageable);

}
