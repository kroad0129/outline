package com.outline.outline.post;

import com.outline.outline.like.Like;
import com.outline.outline.like.LikeRepository;
import com.outline.outline.notification.Notification;
import com.outline.outline.notification.NotificationRepository;
import com.outline.outline.post.dto.*;
import com.outline.outline.region.RegionCode;
import com.outline.outline.region.RegionRepository;
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
    private final OpenAiSummaryService openAiSummaryService;
    private final PostSummaryRepository postSummaryRepository;
    private final RegionRepository regionRepository;
    private final NotificationRepository notificationRepository;

    public PostCreateResponse createPost(PostCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String regionName = request.getRegionName();
        String locationCode = RegionCode.NAME_TO_CODE.getOrDefault(regionName, "UNKNOWN");

        Post post = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .locationCode(locationCode)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .status(request.getStatus())
                .build();

        Post saved = postRepository.save(post);

        List<User> interestedUsers = regionRepository.findAllByLocationCode(locationCode);
        List<Notification> notifications = interestedUsers.stream()
                .map(u -> Notification.builder()
                        .user(u)
                        .message("(" + locationCode + ") 지역에 새로운 제보가 등록되었습니다: " + post.getTitle())
                        .build())
                .toList();
        notificationRepository.saveAll(notifications);

        SummaryResult summary = openAiSummaryService.summarize(request.getTitle(), request.getContent());
        PostSummary postSummary = PostSummary.builder()
                .post(saved)
                .summarizedTitle(summary.getSummarizedTitle())
                .summarizedContent(summary.getSummarizedContent())
                .build();
        postSummaryRepository.save(postSummary);

        return new PostCreateResponse(saved.getId());
    }

    public SummaryResult getPostSummary(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        PostSummary postSummary = postSummaryRepository.findByPost(post)
                .orElseThrow(() -> new IllegalArgumentException("요약 정보가 존재하지 않습니다."));

        return SummaryResult.builder()
                .summarizedTitle(postSummary.getSummarizedTitle())
                .summarizedContent(postSummary.getSummarizedContent())
                .build();
    }

    public List<PostSummaryResponse> getPosts(String sort, String search, String bigCategory, String smallCategory, Long userId) {
        List<Post> posts = postRepository.findByFilters(sort, search, bigCategory, smallCategory);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return posts.stream().map(post -> {
            boolean liked = likeRepository.existsByUserAndPost(user, post);
            PostSummary summary = postSummaryRepository.findByPost(post).orElse(null);
            String locationName = RegionCode.CODE_TO_NAME.get(post.getLocationCode());

            return new PostSummaryResponse(
                    post.getId(),
                    summary != null ? summary.getSummarizedTitle() : post.getTitle(),
                    summary != null ? summary.getSummarizedContent() : post.getContent(),
                    post.getImageUrl(),
                    post.getLocationCode(),
                    locationName,
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
        PostSummary summary = postSummaryRepository.findByPost(post).orElse(null);
        String locationName = RegionCode.CODE_TO_NAME.get(post.getLocationCode());

        return new PostDetailResponse(
                post.getId(),
                summary != null ? summary.getSummarizedTitle() : post.getTitle(),
                summary != null ? summary.getSummarizedContent() : post.getContent(),
                post.getImageUrl(),
                post.getLocationCode(),
                locationName,
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
        return posts.stream().map(post -> {
            PostSummary summary = postSummaryRepository.findByPost(post).orElse(null);
            String locationName = RegionCode.CODE_TO_NAME.get(post.getLocationCode());

            return new PostSummaryResponse(
                    post.getId(),
                    summary != null ? summary.getSummarizedTitle() : post.getTitle(),
                    summary != null ? summary.getSummarizedContent() : post.getContent(),
                    post.getImageUrl(),
                    post.getLocationCode(),
                    locationName,
                    post.getLatitude(),
                    post.getLongitude(),
                    post.getLikeCount(),
                    post.getSolveCount(),
                    post.getStatus(),
                    post.getCreatedAt(),
                    likeRepository.existsByUserAndPost(user, post)
            );
        }).toList();
    }

    public List<PostSummaryResponse> getLikedPosts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        List<Like> likes = likeRepository.findByUser(user);
        return likes.stream().map(like -> {
            Post post = like.getPost();
            PostSummary summary = postSummaryRepository.findByPost(post).orElse(null);
            String locationName = RegionCode.CODE_TO_NAME.get(post.getLocationCode());

            return new PostSummaryResponse(
                    post.getId(),
                    summary != null ? summary.getSummarizedTitle() : post.getTitle(),
                    summary != null ? summary.getSummarizedContent() : post.getContent(),
                    post.getImageUrl(),
                    post.getLocationCode(),
                    locationName,
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
