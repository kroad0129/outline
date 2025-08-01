package com.outline.outline.post;

import com.outline.outline.like.Like;
import com.outline.outline.like.LikeRepository;
import com.outline.outline.post.dto.PostCreateRequest;
import com.outline.outline.post.dto.PostCreateResponse;
import com.outline.outline.post.dto.PostDetailResponse;
import com.outline.outline.post.dto.PostSummaryResponse;
import com.outline.outline.user.User;
import com.outline.outline.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public PostCreateResponse createPost(PostCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Post post = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .locationCode(request.getLocationCode())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .status(request.getStatus())
                .build();

        Post saved = postRepository.save(post);
        return new PostCreateResponse(saved.getId());
    }

    public List<PostSummaryResponse> getPosts(String sort, String search, String bigCategory, String smallCategory, Long userId) {
        List<Post> posts = postRepository.findByFilters(sort, search, bigCategory, smallCategory);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return posts.stream().map(post -> {
            boolean liked = likeRepository.existsByUserAndPost(user, post);
            return new PostSummaryResponse(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getImageUrl(),
                    post.getLocationCode(),
                    post.getLatitude(),
                    post.getLongitude(),
                    post.getLikeCount(),
                    post.getSolveCount(),
                    post.getStatus(),
                    post.getCreatedAt(),
                    liked
            );
        }).toList();
    }

    public PostDetailResponse getPostDetail(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        boolean liked = likeRepository.existsByUserAndPost(user, post);

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImageUrl(),
                post.getLocationCode(),
                post.getLatitude(),
                post.getLongitude(),
                post.getLikeCount(),
                post.getSolveCount(),
                post.getStatus(),
                post.getCreatedAt(),
                post.getUser().getUsername(),
                liked
        );
    }

    public List<PostSummaryResponse> getMyPosts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        List<Post> posts = postRepository.findByUser(user);
        return posts.stream().map(post -> new PostSummaryResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImageUrl(),
                post.getLocationCode(),
                post.getLatitude(),
                post.getLongitude(),
                post.getLikeCount(),
                post.getSolveCount(),
                post.getStatus(),
                post.getCreatedAt(),
                likeRepository.existsByUserAndPost(user, post)
        )).toList();
    }

    public List<PostSummaryResponse> getLikedPosts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        List<Like> likes = likeRepository.findByUser(user);
        return likes.stream().map(like -> {
            Post post = like.getPost();
            return new PostSummaryResponse(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getImageUrl(),
                    post.getLocationCode(),
                    post.getLatitude(),
                    post.getLongitude(),
                    post.getLikeCount(),
                    post.getSolveCount(),
                    post.getStatus(),
                    post.getCreatedAt(),
                    true
            );
        }).toList();
    }

    public record CategoryStatusCount(String bigCategory, Long count) {}

    public List<CategoryStatusCount> getUnresolvedCountsByCategory(String bigCategory) {
        if (bigCategory == null || bigCategory.isBlank()) {
            return postRepository.countUnresolvedByAllBigCategory().stream()
                    .map(row -> new CategoryStatusCount((String) row[0], ((Number) row[1]).longValue()))
                    .toList();
        } else {
            Long count = postRepository.countUnresolvedByBigCategory(bigCategory);
            return List.of(new CategoryStatusCount(bigCategory, count));
        }
    }
}
