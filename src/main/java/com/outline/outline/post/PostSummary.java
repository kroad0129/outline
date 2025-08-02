package com.outline.outline.post;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column(length = 100)
    private String summarizedTitle;

    @Column(columnDefinition = "TEXT")
    private String summarizedContent;
}

