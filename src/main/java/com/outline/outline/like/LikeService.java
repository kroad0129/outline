package com.outline.outline.like;

import com.outline.outline.like.dto.LikeRequest;
import com.outline.outline.post.Post;
import com.outline.outline.post.PostRepository;
import com.outline.outline.user.User;
import com.outline.outline.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void addLike(LikeRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new IllegalStateException("이미 공감한 게시글입니다.");
        }

        likeRepository.save(new Like(user, post));
        post.setLikeCount(post.getLikeCount() + 1);
    }

    @Transactional
    public void removeLike(LikeRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new IllegalStateException("공감하지 않은 게시글입니다."));

        likeRepository.delete(like);
        post.setLikeCount(post.getLikeCount() - 1);
    }

    public boolean hasLiked(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        return likeRepository.existsByUserAndPost(user, post);
    }

}
