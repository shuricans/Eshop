package ru.geekbrains.service;

import ru.geekbrains.service.dto.OrderDto;

import java.util.List;

public interface OrderService {

    void createOrder(String login);

    List<OrderDto> findOrdersByCustomerLogin(String login);
}
