package ru.geekbrains.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.repository.BrandRepository;
import ru.geekbrains.persist.repository.CategoryRepository;
import ru.geekbrains.persist.repository.ProductRepository;
import ru.geekbrains.persist.specification.ProductSpecification;
import ru.geekbrains.service.dto.ProductDto;
import ru.geekbrains.service.dto.ProductMapper;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;
    private final PictureService pictureService;

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

    @Override
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ProductDto save(ProductDto productDto) {
        Long productId = productDto.getId();
        Product product = (productId != null) ?
                (productRepository.findById(productId).orElseThrow(() ->
                        new NotFoundException("Product with id = " + productId + "does not exist.")))
                : new Product();

        Long categoryId = productDto.getCategory().getId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category with id = " + categoryId + " not found"));

        Long brandId = productDto.getBrand().getId();
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand with id = " + brandId + " not found"));

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setBrand(brand);

        MultipartFile[] newPictures = productDto.getNewPictures();
        if (newPictures != null) {
            for (MultipartFile newPicture : newPictures) {
                if (newPicture.isEmpty()) continue;
                try {
                    product.getPictures().add(
                            new Picture(null,
                                    newPicture.getOriginalFilename(),
                                    newPicture.getContentType(),
                                    pictureService.createPicture(newPicture.getBytes()),
                                    product));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        product = productRepository.save(product);
        return productMapper.fromProduct(product);
    }

    private <T> Specification<T> combineSpec(Specification<T> s1, Specification<T> s2) {
        return s1 == null ? Specification.where(s2) : s1.and(s2);
    }
}
