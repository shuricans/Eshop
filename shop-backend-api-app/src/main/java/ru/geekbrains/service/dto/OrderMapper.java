package ru.geekbrains.service.dto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.geekbrains.persist.model.Order;

@Mapper(componentModel = "spring", uses = {OrderLineItemMapper.class})
public interface OrderMapper {

    @InheritInverseConfiguration
    @Mapping(target = "login", source = "customer.user.login")
    OrderDto fromOrder(Order order);
}
