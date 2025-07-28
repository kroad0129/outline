package com.outline.outline.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostCreateRequest {

    private Long userId;          // 작성자
    private String title;         // 제목
    private String content;       // 내용
    private String imageUrl;      // 이미지 URL (선택)
    private String locationCode;  // 지역 코드 예: 1-1
    private int status;           // 0=해결안됨, 1=진행중, 2=해결됨
}
