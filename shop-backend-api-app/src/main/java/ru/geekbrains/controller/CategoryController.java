package ru.geekbrains.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.dto.CategoryDto;

import java.util.List;

@Tag(name = "Category", description = "Service to get categories information")
@AllArgsConstructor
@RestController
@RequestMapping("/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }
}
