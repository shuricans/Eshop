package ru.geekbrains.service.dto;

import lombok.*;

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

    private List<OrderLineItemDto> orderLineItems;
}
