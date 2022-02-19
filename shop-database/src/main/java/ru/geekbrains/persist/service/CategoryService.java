package ru.geekbrains.persist.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.geekbrains.persist.service.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Page<CategoryDto> findAll(Optional<String> nameFilter,
                              Integer page,
                              Integer size,
                              String sortField,
                              Sort.Direction direction);

    List<CategoryDto> findAll();

    Optional<CategoryDto> findById(Long categoryId);

    void deleteById(Long id);

    CategoryDto save(CategoryDto category);
}
