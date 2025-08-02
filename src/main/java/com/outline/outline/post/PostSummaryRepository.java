package com.outline.outline.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostSummaryRepository extends JpaRepository<PostSummary, Long> {
    Optional<PostSummary> findByPost(Post post);
}
