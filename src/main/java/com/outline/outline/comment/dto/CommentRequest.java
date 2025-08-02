package com.outline.outline.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequest {
    private Long userId;
    private String content;
}
