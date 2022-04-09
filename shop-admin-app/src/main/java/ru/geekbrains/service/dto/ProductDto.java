package ru.geekbrains.service.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    @PositiveOrZero
    @NotNull(message = "please set the price")
    private BigDecimal price;

    private CategoryDto category;

    private BrandDto brand;

    private MultipartFile[] newPictures;

    private List<Long> pictures;
}
