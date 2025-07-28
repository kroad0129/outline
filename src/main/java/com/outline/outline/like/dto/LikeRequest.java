package com.outline.outline.like.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LikeRequest {
    private Long userId;
    private Long postId;
}
