package com.outline.outline.region;

import com.outline.outline.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findAllByUser(User user);
    boolean existsByUserAndLocationCode(User user, String locationCode);
    void deleteByUserAndLocationCode(User user, String locationCode);

    @Query("SELECT r.user FROM Region r WHERE r.locationCode = :locationCode")
    List<User> findAllByLocationCode(@Param("locationCode") String locationCode);
}

