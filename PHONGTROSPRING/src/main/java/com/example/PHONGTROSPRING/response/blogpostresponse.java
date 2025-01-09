package com.example.PHONGTROSPRING.response;

import java.time.LocalDateTime;

import com.example.PHONGTROSPRING.entities.User;

public class blogpostresponse {

    private int postId;
    private User user;
    private String title;
    private String message;
    private LocalDateTime createdAt;

    // Constructor
    public blogpostresponse(int postId, User user, String title, String message, LocalDateTime createdAt) {
        this.postId = postId;
        this.user = user;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
