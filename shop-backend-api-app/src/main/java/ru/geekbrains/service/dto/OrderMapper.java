package ru.geekbrains.service.dto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.geekbrains.persist.model.Order;

@Mapper(componentModel = "spring", uses = {OrderLineItemMapper.class})
public interface OrderMapper {

    @InheritInverseConfiguration
    OrderDto fromOrder(Order order);
}
