package com.outline.outline.comment.dto;

import com.outline.outline.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private User user;
    private String content;
    private LocalDateTime createdAt;
}
