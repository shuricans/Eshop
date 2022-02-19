package ru.geekbrains.persist.service.dto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.geekbrains.persist.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryDto categoryDto);

    @InheritInverseConfiguration
    CategoryDto fromCategory(Category category);
}
