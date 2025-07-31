package com.outline.outline.solve;

import com.outline.outline.solve.dto.SolveRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solve")
@RequiredArgsConstructor
@Tag(name = "Solve", description = "해결됨 체크 관련 API")
public class SolveController {

    private final SolveService solveService;

    @PostMapping
    @Operation(summary = "해결됨 등록", description = "사용자가 게시글을 해결되었다고 표시합니다.")
    public void solve(@RequestBody SolveRequest request) {
        solveService.addSolve(request);
    }

    @DeleteMapping
    @Operation(summary = "해결됨 취소", description = "사용자가 해결됨 표시를 취소합니다.")
    public void cancelSolve(@RequestBody SolveRequest request) {
        solveService.removeSolve(request);
    }

    @GetMapping("/check")
    @Operation(summary = "해결 여부 확인", description = "특정 사용자가 특정 게시글에 해결됨 표시를 했는지 확인합니다.")
    public boolean checkSolve(
            @RequestParam Long userId,
            @RequestParam Long postId
    ) {
        return solveService.hasSolved(userId, postId);
    }
}
