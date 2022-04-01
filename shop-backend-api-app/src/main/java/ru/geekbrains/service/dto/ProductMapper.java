package ru.geekbrains.service.dto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, BrandMapper.class})
public interface ProductMapper {

    @Mapping(target = "pictures", ignore = true)
    Product toProduct(ProductDto productDto);

    @InheritInverseConfiguration
    @Mapping(source = "pictures", target = "pictures", qualifiedBy = PicturesToIds.class)
    ProductDto fromProduct(Product product);

    @PicturesToIds
    static List<Long> picturesToIds(List<Picture> pictures) {
        return pictures.stream().map(Picture::getId).collect(Collectors.toList());
    }
}
