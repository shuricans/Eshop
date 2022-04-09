package ru.geekbrains.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddLineItemDto {

    private Long productId;

    private Integer qty;

    private String color;

    private String material;
}
