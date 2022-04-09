package ru.geekbrains.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.service.OrderService;
import ru.geekbrains.service.dto.OrderDto;

import java.util.List;

@Tag(name = "Order", description = "Controller to get orders information")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping("/v1/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void createOrder(Authentication auth) {
        orderService.createOrder(auth.getName());
    }

    @GetMapping("/all")
    public List<OrderDto> findAll(Authentication auth) {
        return orderService.findOrdersByCustomerLogin(auth.getName());
    }

    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable("id") Long id, Authentication auth) {
        return orderService.findOrderByOrderIdAndCustomerLogin(id, auth.getName())
                .orElseThrow(RuntimeException::new);
    }

    @GetMapping("/cancel/{id}")
    public void cancelOrder(@PathVariable("id") Long id, Authentication auth) {
        orderService.cancelOrderByIdAndCustomerLogin(id, auth.getName());
    }
}
