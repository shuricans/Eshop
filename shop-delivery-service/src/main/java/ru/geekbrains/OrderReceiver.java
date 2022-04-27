package ru.geekbrains;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.geekbrains.service.dto.OrderStatus;

@Slf4j
@RequiredArgsConstructor
@RabbitListener(queues = "new.order.queue")
public class OrderReceiver {

    private final AmqpTemplate rabbitTemplate;

    @RabbitHandler
    public void receive(OrderStatus orderStatus) {
        log.info("New order received for processing '{}'", orderStatus.getOrderId());

        new Thread(() -> {
            for (OrderStatus.Value status : OrderStatus.Value.values()) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("Sending next status {} for order {}", status, orderStatus.getOrderId());
                orderStatus.setStatus(status.toString());
                rabbitTemplate.convertAndSend("order.exchange", "processed_order", orderStatus);
            }
        }).start();
    }
}
