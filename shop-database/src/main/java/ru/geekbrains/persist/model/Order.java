package ru.geekbrains.persist.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ordr")
public class Order {

    @Id
    @SequenceGenerator(
            name = "orders_sequence",
            sequenceName = "orders_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "orders_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "order",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<OrderLineItem> orderLineItems;

    public enum OrderStatus {
        CREATED, PROCESSED, IN_DELIVERY, DELIVERED, CLOSED, CANCELED
    }
}
