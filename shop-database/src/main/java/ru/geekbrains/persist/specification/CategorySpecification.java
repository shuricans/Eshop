package ru.geekbrains.persist.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.model.Category;

public class CategorySpecification {

    public static Specification<Category> nameLike(String pattern) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + pattern + "%");
    }
}
