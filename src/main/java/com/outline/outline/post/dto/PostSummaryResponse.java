package com.outline.outline.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostSummaryResponse {
    private Long id;
    private String title;
    private String content;
    private List<String> imageUrl;
    private String locationCode;
    private String locationName;
    private Double latitude;
    private Double longitude;
    private int likeCount;
    private int solveCount;
    private int status;
    private LocalDateTime createdAt;
    private boolean liked;
}
