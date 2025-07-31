package com.outline.outline.post;

import com.outline.outline.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findByUser(User user);

    @Query(value = """
    SELECT SUBSTRING_INDEX(location_code, '-', 1) AS bigCategory,
           COUNT(*) AS count
    FROM post
    WHERE status = 0
    GROUP BY bigCategory
    ORDER BY bigCategory
""", nativeQuery = true)
    List<Object[]> countUnresolvedByAllBigCategory();

    @Query(value = """
    SELECT COUNT(*) FROM post
    WHERE status = 0 AND location_code LIKE CONCAT(:bigCategory, '-%')
""", nativeQuery = true)
    Long countUnresolvedByBigCategory(@Param("bigCategory") String bigCategory);
}
