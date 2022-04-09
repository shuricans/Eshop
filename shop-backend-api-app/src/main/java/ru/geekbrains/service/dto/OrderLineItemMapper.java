package ru.geekbrains.service.dto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.geekbrains.persist.model.OrderLineItem;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderLineItemMapper {

    @InheritInverseConfiguration
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "orderId", source = "order.id")
    OrderLineItemDto fromOrderLineItem(OrderLineItem orderLineItem);
}
