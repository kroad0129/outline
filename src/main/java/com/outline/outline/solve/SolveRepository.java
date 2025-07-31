package com.outline.outline.solve;

import com.outline.outline.post.Post;
import com.outline.outline.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SolveRepository extends JpaRepository<Solve, Long> {
    boolean existsByUserAndPost(User user, Post post);
    long countByPost(Post post);
    Optional<Solve> findByUserAndPost(User user, Post post);
    @Query("SELECT s.user FROM Solve s WHERE s.post = :post")
    List<User> findUsersByPost(@Param("post") Post post);
}
