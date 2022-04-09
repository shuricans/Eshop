package ru.geekbrains.service;

import ru.geekbrains.service.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void createOrder(String login);

    void cancelOrderByIdAndCustomerLogin(long id, String login);

    List<OrderDto> findOrdersByCustomerLogin(String login);

    Optional<OrderDto> findOrderByOrderIdAndCustomerLogin(long id, String login);
}
