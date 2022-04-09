package ru.geekbrains.service.dto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.geekbrains.persist.model.Brand;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    Brand toBrand(BrandDto brandDto);

    @InheritInverseConfiguration
    BrandDto fromBrand(Brand brand);
}
