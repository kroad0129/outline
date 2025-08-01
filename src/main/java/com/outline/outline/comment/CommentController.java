package com.outline.outline.comment;

import com.outline.outline.comment.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> writeComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto request
    ) {
        commentService.writeComment(postId, request);
        return ResponseEntity.ok().build();
    }
}
