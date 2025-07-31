package com.outline.outline.solve;

import com.outline.outline.post.Post;
import com.outline.outline.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_solve")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Solve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Solve(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
