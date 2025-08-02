package com.outline.outline.post;

import com.outline.outline.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 500)
    private String imageUrl;

    @Column(length = 10, nullable = false)
    private String locationCode;  // 예: "1-1"

    @Column(nullable = true)
    private Double latitude;

    @Column(nullable = true)
    private Double longitude;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private int solveCount = 0;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private int status;  // 0: 진행중, 1: 제보됨, 2: 해결됨

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
