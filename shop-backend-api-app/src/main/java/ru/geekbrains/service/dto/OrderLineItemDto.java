package ru.geekbrains.service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemDto {

    private Long id;

    private Integer qty;

    private BigDecimal price;

    private String color;

    private String material;

    private Long productId;

    private Long orderId;
}
