package com.outline.outline.like;

import com.outline.outline.post.Post;
import com.outline.outline.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPost(User user, Post post);
    Optional<Like> findByUserAndPost(User user, Post post);

    @Query("SELECT l.user FROM Like l WHERE l.post = :post")
    List<User> findUsersByPost(@Param("post") Post post);
    List<Like> findByUser(User user);
}

