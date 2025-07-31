package com.outline.outline.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostSummaryResponse {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private int likeCount;
    private int solveCount;
    private int status;
    private LocalDateTime createdAt;
    private boolean liked;
}
