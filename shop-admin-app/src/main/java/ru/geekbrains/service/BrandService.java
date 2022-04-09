package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.geekbrains.service.dto.BrandDto;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Page<BrandDto> findAll(Optional<String> nameFilter,
                               Integer page,
                               Integer size,
                               String sortField,
                               Sort.Direction direction);

    List<BrandDto> findAll();

    Optional<BrandDto> findById(Long brandId);

    void deleteById(Long id);

    BrandDto save(BrandDto brandDto);
}
