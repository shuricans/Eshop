package ru.geekbrains.persist.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.model.Brand;

public class BrandSpecification {

    public static Specification<Brand> nameLike(String pattern) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + pattern + "%");
    }
}
