package ru.geekbrains.service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private LocalDateTime date;

    private String status;

    private String login;

    private BigDecimal price;

    private List<OrderLineItemDto> orderLineItems;
}
