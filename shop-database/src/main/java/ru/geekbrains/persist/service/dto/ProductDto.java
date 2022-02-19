package ru.geekbrains.persist.service.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
