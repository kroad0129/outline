package com.outline.outline.post;

import com.outline.outline.post.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "Post", description = "게시글(제보) 관련 API")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "게시글 등록", description = "제목, 내용, 사진, 위치 등을 포함한 게시글을 등록합니다.")
    public PostCreateResponse create(@RequestBody PostCreateRequest request) {
        return postService.createPost(request);
    }

    @GetMapping
    @Operation(summary = "게시글 전체 조회", description = "정렬조건(latest, like), 검색어, 지역 필터, 사용자 ID를 기반으로 게시글을 조회합니다.")
    public List<PostSummaryResponse> getPosts(
            @RequestParam(defaultValue = "latest") String sort,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String bigCategory,
            @RequestParam(required = false) String smallCategory,
            @RequestParam Long userId
    ) {
        return postService.getPosts(sort, search, bigCategory, smallCategory, userId);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시글 상세 조회", description = "게시글 ID와 사용자 ID로 상세 내용을 조회합니다.")
    public PostDetailResponse getPostDetail(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        return postService.getPostDetail(postId, userId);
    }

    @GetMapping("/mine")
    @Operation(summary = "내가 쓴 글 조회")
    public List<PostSummaryResponse> getMyPosts(@RequestParam Long userId) {
        return postService.getMyPosts(userId);
    }

    @GetMapping("/liked")
    @Operation(summary = "내가 공감한 글 조회")
    public List<PostSummaryResponse> getLikedPosts(@RequestParam Long userId) {
        return postService.getLikedPosts(userId);
    }

    @GetMapping("/stats/unresolved")
    @Operation(summary = "미해결 제보 통계", description = "빅카테고리별 status=0 게시글 수를 조회합니다. (빈칸- 모든 빅카테고리 조회)")
    public List<PostService.CategoryStatusCount> getUnresolvedStats(
            @RequestParam(required = false) String bigCategory
    ) {
        return postService.getUnresolvedCountsByCategory(bigCategory);
    }

    @GetMapping("/{postId}/summary")
    @Operation(summary = "게시글 요약 조회", description = "AI가 요약한 제목과 내용을 제공합니다")
    public SummaryResult getPostSummary(@PathVariable Long postId) {
        return postService.getPostSummary(postId);
    }

}
