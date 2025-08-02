package com.outline.outline.region;

import com.outline.outline.region.dto.RegionRequest;
import com.outline.outline.region.dto.RegionResponse;
import com.outline.outline.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public void addRegion(User user, RegionRequest request) {
        // 중복 방지
        if (regionRepository.existsByUserAndLocationCode(user, request.getLocationCode())) {
            throw new IllegalArgumentException("이미 등록된 관심지역입니다.");
        }

        Region region = Region.builder()
                .user(user)
                .locationCode(request.getLocationCode())
                .build();

        regionRepository.save(region);
    }

    public void removeRegion(User user, String locationCode) {
        if (!regionRepository.existsByUserAndLocationCode(user, locationCode)) {
            throw new IllegalArgumentException("등록되지 않은 관심지역입니다.");
        }

        regionRepository.deleteByUserAndLocationCode(user, locationCode);
    }

    public List<RegionResponse> getUserRegions(User user) {
        return regionRepository.findAllByUser(user).stream()
                .map(r -> new RegionResponse(r.getLocationCode()))
                .collect(Collectors.toList());
    }
}
