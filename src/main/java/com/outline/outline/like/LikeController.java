package com.outline.outline.like;

import com.outline.outline.like.dto.LikeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@Tag(name = "Like", description = "좋아요(공감) 관련 API")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    @Operation(summary = "좋아요 등록", description = "사용자가 게시글에 공감합니다.")
    public void addLike(@RequestBody LikeRequest request) {
        likeService.addLike(request);
    }

    @DeleteMapping
    @Operation(summary = "좋아요 취소", description = "사용자가 게시글 공감을 취소합니다.")
    public void removeLike(@RequestBody LikeRequest request) {
        likeService.removeLike(request);
    }

    @GetMapping("/check")
    @Operation(summary = "공감 여부 확인", description = "특정 사용자가 특정 게시글에 공감했는지 여부를 확인합니다.")
    public boolean checkLike(
            @RequestParam Long userId,
            @RequestParam Long postId
    ) {
        return likeService.hasLiked(userId, postId);
    }

}
