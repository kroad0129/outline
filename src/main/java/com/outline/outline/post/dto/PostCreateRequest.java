package com.outline.outline.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PostCreateRequest {

    private Long userId;          // 작성자
    private String title;         // 제목
    private String content;       // 내용
    private List<String> imageUrl;      // 이미지 URL (선택)
    private String locationCode;  // 지역 코드 예: 1-1
    private String regionName; // 지역명
    private Double latitude;   // 지도용
    private Double longitude;  // 지도용
    private int status;           // 0=해결안됨, 1=진행중, 2=해결됨
}
