package com.outline.outline.post;

import com.outline.outline.post.Post;
import java.util.List;

public interface PostRepositoryCustom {
    List<Post> findByFilters(String sort, String search, String bigCategory, String smallCategory);
}
