package ru.geekbrains.service;

import ru.geekbrains.service.dto.LineItem;
import ru.geekbrains.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    void addProductQty(ProductDto productDto, String color, String material, int qty);

    void removeProductQty(ProductDto productDto, String color, String material, int qty);

    void removeProduct(ProductDto productDto, String color, String material);

    void removeAll();

    List<LineItem> getLineItems();

    BigDecimal getSubTotal();
}
