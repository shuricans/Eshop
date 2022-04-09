package ru.geekbrains.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.service.BrandService;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.dto.ProductDto;


import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("nameFilter") Optional<String> nameFilter,
                           @RequestParam("minPrice") Optional<BigDecimal> minPrice,
                           @RequestParam("maxPrice") Optional<BigDecimal> maxPrice,
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
        model.addAttribute(
                "products",
                productService.findAll(
                        nameFilter,
                        minPrice,
                        maxPrice,
                        page.orElse(1) - 1,
                        size.orElse(10),
                        sortBy,
                        sortDir.orElse(Sort.Direction.ASC)
                ));
        return "product";
    }

    @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found")));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        return "product_form";
    }

    @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        return "product_form";
    }

    @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    @PostMapping
    public String save(
            @Valid @ModelAttribute("product") ProductDto product,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product_form";
        }
        productService.save(product);
        return "redirect:/product";
    }

    @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        logger.info("You will try to delete a product with = {}", id);
        if (productService.existsById(id)) {
            productService.deleteById(id);
            logger.info("Product with id = {} deleted successfully", id);
        } else {
            logger.info("id = {} does not exist", id);
        }
        return "redirect:/product";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(NotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "not_found";
    }
}
