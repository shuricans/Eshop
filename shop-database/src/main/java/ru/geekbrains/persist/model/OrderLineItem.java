package ru.geekbrains.persist.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_line_item")
public class OrderLineItem {

    @Id
    @SequenceGenerator(
            name = "order_line_items_sequence",
            sequenceName = "order_line_items_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "order_line_items_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Column(name = "order_line_item_id")
    private Long id;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false, updatable = false)
    private BigDecimal price;

    @Column
    private String color;

    @Column
    private String material;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
