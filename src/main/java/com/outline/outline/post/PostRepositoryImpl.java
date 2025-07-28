package com.outline.outline.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.util.StringUtils;

import java.util.List;

public class PostRepositoryImpl implements PostRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Post> findByFilters(String sort, String search, String bigCategory, String smallCategory) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Post p WHERE 1=1");

        if (StringUtils.hasText(search)) {
            jpql.append(" AND (p.title LIKE :search OR p.content LIKE :search)");
        }

        if (StringUtils.hasText(bigCategory)) {
            jpql.append(" AND p.locationCode LIKE :bigCategoryPattern");
        }

        if (StringUtils.hasText(smallCategory)) {
            jpql.append(" AND p.locationCode = :smallCategory");
        }

        if ("like".equals(sort)) {
            jpql.append(" ORDER BY p.likeCount DESC");
        } else {
            jpql.append(" ORDER BY p.createdAt DESC");
        }

        TypedQuery<Post> query = em.createQuery(jpql.toString(), Post.class);

        if (StringUtils.hasText(search)) {
            query.setParameter("search", "%" + search + "%");
        }

        if (StringUtils.hasText(bigCategory)) {
            query.setParameter("bigCategoryPattern", bigCategory + "-%");
        }

        if (StringUtils.hasText(smallCategory)) {
            query.setParameter("smallCategory", smallCategory);
        }

        return query.getResultList();
    }
}
