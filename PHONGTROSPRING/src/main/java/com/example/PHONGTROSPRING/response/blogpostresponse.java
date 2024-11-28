package com.example.PHONGTROSPRING.response;

import java.time.LocalDateTime;

import com.example.PHONGTROSPRING.entities.User;

public class blogpostresponse {
	private int postId;
	private User user;
	private String title;
	private String message;
	private LocalDateTime createdAt;
}
