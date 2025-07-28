package com.outline.outline.like;

import com.outline.outline.post.Post;
import com.outline.outline.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_like")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
