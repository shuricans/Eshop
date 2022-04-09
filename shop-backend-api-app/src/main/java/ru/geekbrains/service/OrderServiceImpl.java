package ru.geekbrains.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.model.Order;
import ru.geekbrains.persist.model.OrderLineItem;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.repository.OrderRepository;
import ru.geekbrains.service.dto.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CustomerService customerService;
    private final ProductService productService;

    private final OrderMapper orderMapper;
    private final CustomerMapper customerMapper;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public void createOrder(String login) {
        if (cartService.getLineItems().isEmpty()) {
            logger.info("Can't create order for empty Cart");
            return;
        }

        CustomerDto customerDto = customerService.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.save(
                new Order(
                        null,
                        LocalDateTime.now(),
                        cartService.getSubTotal(),
                        Order.OrderStatus.CREATED,
                        customerMapper.toCustomer(customerDto),
                        null
                ));

        List<OrderLineItem> orderLineItems = cartService.getLineItems()
                .stream()
                .map(li -> new OrderLineItem(
                        null,
                        li.getQty(),
                        li.getItemTotal(),
                        li.getColor(),
                        li.getMaterial(),
                        findProductById(li.getProductId()),
                        order
                ))
                .collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);
        orderRepository.save(order);
        cartService.removeAll();
    }

    @Override
    public void cancelOrderByIdAndCustomerLogin(long id, String login) {
        orderRepository.findByIdAndLogin(id, login).ifPresent(order -> {
            order.setStatus(Order.OrderStatus.CANCELED);
            orderRepository.save(order);
        });
    }

    @Override
    public List<OrderDto> findOrdersByCustomerLogin(String login) {
        return orderRepository.findAllByLogin(login)
                .stream()
                .map(orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDto> findOrderByOrderIdAndCustomerLogin(long id, String login) {
        return orderRepository.findByIdAndLogin(id, login).map(orderMapper::fromOrder);
    }

    private Product findProductById(Long id) {
        return productService.findById(id)
                .map(productMapper::toProduct)
                .orElseThrow(() -> new RuntimeException("No product with id"));
    }
}
