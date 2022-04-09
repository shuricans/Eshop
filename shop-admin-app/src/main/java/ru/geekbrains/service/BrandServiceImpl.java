package ru.geekbrains.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.repository.BrandRepository;
import ru.geekbrains.persist.specification.BrandSpecification;
import ru.geekbrains.service.dto.BrandDto;
import ru.geekbrains.service.dto.BrandMapper;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public Page<BrandDto> findAll(Optional<String> nameFilter,
                                  Integer page,
                                  Integer size,
                                  String sortField,
                                  Sort.Direction direction) {

        Specification<Brand> spec = null;

        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec = Specification.where(
                    BrandSpecification.nameLike(nameFilter.get().toLowerCase(Locale.ROOT)));
        }

        spec = combineSpec(spec, Specification.where(null));

        return brandRepository.findAll(spec, PageRequest.of(page, size, Sort.by(direction, sortField)))
                .map(brandMapper::fromBrand);
    }

    @Override
    public List<BrandDto> findAll() {
        return brandRepository.findAll().stream()
                .map(brandMapper::fromBrand)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BrandDto> findById(Long brandId) {
        return brandRepository.findById(brandId)
                .map(brandMapper::fromBrand);
    }

    @Override
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public BrandDto save(BrandDto brandDto) {
        Brand brand = brandMapper.toBrand(brandDto);
        brand = brandRepository.save(brand);
        return brandMapper.fromBrand(brand);
    }

    private <T> Specification<T> combineSpec(Specification<T> s1, Specification<T> s2) {
        return s1 == null ? Specification.where(s2) : s1.and(s2);
    }
}
