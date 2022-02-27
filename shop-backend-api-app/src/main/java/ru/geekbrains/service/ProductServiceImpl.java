package ru.geekbrains.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.repository.ProductRepository;
import ru.geekbrains.persist.specification.ProductSpecification;
import ru.geekbrains.service.dto.ProductDto;
import ru.geekbrains.service.dto.ProductMapper;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductDto> findAll(
            Optional<String> nameFilter,
            Optional<BigDecimal> minPrice,
            Optional<BigDecimal> maxPrice,
            Integer page,
            Integer size,
            String sortField,
            Sort.Direction direction) {
        Specification<Product> spec = null;

        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec = Specification.where(
                    ProductSpecification.nameLike(nameFilter.get().toLowerCase(Locale.ROOT)));
        }

        if (minPrice.isPresent()) {
            spec = combineSpec(spec, ProductSpecification.minPriceFilter(minPrice.get()));
        }

        if (maxPrice.isPresent()) {
            spec = combineSpec(spec, ProductSpecification.maxPriceFilter(maxPrice.get()));
        }

        spec = combineSpec(spec, Specification.where(null));

        return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(direction, sortField)))
                .map(productMapper::fromProduct);
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::fromProduct);
    }

    private <T> Specification<T> combineSpec(Specification<T> s1, Specification<T> s2) {
        return s1 == null ? Specification.where(s2) : s1.and(s2);
    }
}
