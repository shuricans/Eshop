package ru.geekbrains.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Optional;

@Tag(name = "Product", description = "Service to get product information")
@AllArgsConstructor
@RestController
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public Page<ProductDto> findAll(@RequestParam("nameFilter") Optional<String> nameFilter,
                                    @RequestParam("minPrice") Optional<BigDecimal> minPrice,
                                    @RequestParam("maxPrice") Optional<BigDecimal> maxPrice,
                                    @RequestParam("categoryId") Optional<Long> categoryId,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size,
                                    @RequestParam("sortField") Optional<String> sortField,
                                    @RequestParam("sortDir") Optional<Sort.Direction> sortDir) {

        String sortBy;
        if (sortField.isPresent() && !sortField.get().isEmpty()) {
            sortBy = sortField.get();
        } else {
            sortBy = "id";
        }

        return productService.findAll(
                nameFilter,
                minPrice,
                maxPrice,
                categoryId,
                page.orElse(1) - 1,
                size.orElse(10),
                sortBy,
                sortDir.orElse(Sort.Direction.ASC));
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable("id") Long id) {
        return productService.findById(id).orElseThrow(RuntimeException::new);
    }
}
