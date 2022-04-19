package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.service.dto.LineItem;
import ru.geekbrains.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImpl();
    }

    @Test
    public void testIfNewCartIsEmpty() {
        assertNotNull(cartService.getLineItems());
        assertTrue(cartService.getLineItems().isEmpty());
        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testAddProductQty() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setPrice(new BigDecimal(123));
        expectedProduct.setName("Product name");

        cartService.addProductQty(expectedProduct, "color", "material", 2);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        assertEquals("color", lineItem.getColor());
        assertEquals("material", lineItem.getMaterial());
        assertEquals(2, lineItem.getQty());

        assertEquals(expectedProduct.getId(), lineItem.getProductId());
        assertNotNull(lineItem.getProductDto());
        assertEquals(expectedProduct.getName(), lineItem.getProductDto().getName());
    }

    @Test
    public void testRemoveProductQty() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        BigDecimal price = new BigDecimal(123);
        expectedProduct.setPrice(price);
        expectedProduct.setName("Product name");

        String color = "color";
        String material = "material";

        cartService.addProductQty(expectedProduct, color, material, 2);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        cartService.removeProductQty(expectedProduct, color, material, 1);

        assertTrue(cartService.getLineItems().get(0).getQty() == 1);
        assertEquals(price, cartService.getSubTotal());
    }

    @Test
    public void testRemoveProduct() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setPrice(new BigDecimal(123));
        expectedProduct.setName("Product name");

        String color = "color";
        String material = "material";

        cartService.addProductQty(expectedProduct, color, material, 2);
        // remove product completely
        cartService.removeProduct(expectedProduct, color, material);

        assertTrue(cartService.getLineItems().isEmpty());
        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testRemoveAll() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setPrice(new BigDecimal(123));
        expectedProduct.setName("Product name");

        String color = "color";
        String material = "material";

        cartService.addProductQty(expectedProduct, color, material, 2);

        cartService.removeAll();
        assertTrue(cartService.getLineItems().isEmpty());
        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testGetLineItems() {
        ProductDto product_1 = new ProductDto();
        product_1.setId(1L);
        product_1.setPrice(new BigDecimal(100));
        product_1.setName("Product name_1");

        ProductDto product_2 = new ProductDto();
        product_2.setId(2L);
        product_2.setPrice(new BigDecimal(200));
        product_2.setName("Product name_2");

        String color = "color";
        String material = "material";

        LineItem expectedLineItem_1 = new LineItem(product_1, color, material);
        LineItem expectedLineItem_2 = new LineItem(product_2, color, material);

        cartService.addProductQty(product_1, color, material, 1);
        cartService.addProductQty(product_2, color, material, 2);

        List<LineItem> lineItems = cartService.getLineItems();

        assertEquals(2, lineItems.size());
        Optional<LineItem> first = lineItems.stream()
                .filter(li -> li.getProductId().equals(expectedLineItem_1.getProductId())).findFirst();
        assertTrue(first.isPresent());
        assertEquals(expectedLineItem_1, first.get());

        Optional<LineItem> second = lineItems.stream()
                .filter(li -> li.getProductId().equals(expectedLineItem_2.getProductId())).findFirst();
        assertTrue(second.isPresent());
        assertEquals(expectedLineItem_2, second.get());
    }
}
