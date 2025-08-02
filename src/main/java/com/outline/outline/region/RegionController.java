package com.outline.outline.region;

import com.outline.outline.region.dto.RegionRequest;
import com.outline.outline.region.dto.RegionResponse;
import com.outline.outline.user.User;
import com.outline.outline.user.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
@Tag(name = "Region", description = "유저 관심지역 API")
public class RegionController {

    private final RegionService regionService;
    private final UserRepository userRepository;

    // 관심지역 추가
    @PostMapping
    public ResponseEntity<String> addRegion(@RequestBody RegionRequest request) {
        User user = getUserFromId(request.getUserId());
        regionService.addRegion(user, request);
        return ResponseEntity.ok("관심지역 등록 완료");
    }

    // 관심지역 삭제
    @DeleteMapping("/{userId}/{locationCode}")
    public ResponseEntity<String> removeRegion(@PathVariable Long userId, @PathVariable String locationCode) {
        User user = getUserFromId(userId);
        regionService.removeRegion(user, locationCode);
        return ResponseEntity.ok("관심지역 삭제 완료");
    }

    // 관심지역 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<RegionResponse>> getUserRegions(@PathVariable Long userId) {
        User user = getUserFromId(userId);
        List<RegionResponse> regions = regionService.getUserRegions(user);
        return ResponseEntity.ok(regions);
    }

    private User getUserFromId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
    }
}
