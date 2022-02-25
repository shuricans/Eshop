package ru.geekbrains.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.repository.CategoryRepository;
import ru.geekbrains.persist.specification.CategorySpecification;
import ru.geekbrains.service.dto.CategoryDto;
import ru.geekbrains.service.dto.CategoryMapper;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> findAll(Optional<String> nameFilter,
                                     Integer page,
                                     Integer size,
                                     String sortField,
                                     Sort.Direction direction) {

        Specification<Category> spec = null;

        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec = Specification.where(
                    CategorySpecification.nameLike(nameFilter.get().toLowerCase(Locale.ROOT)));
        }

        spec = combineSpec(spec, Specification.where(null));

        return categoryRepository.findAll(spec, PageRequest.of(page, size, Sort.by(direction, sortField)))
                .map(categoryMapper::fromCategory);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::fromCategory)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDto> findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::fromCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        category = categoryRepository.save(category);
        return categoryMapper.fromCategory(category);
    }

    private <T> Specification<T> combineSpec(Specification<T> s1, Specification<T> s2) {
        return s1 == null ? Specification.where(s2) : s1.and(s2);
    }
}