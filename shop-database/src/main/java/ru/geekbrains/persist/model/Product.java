package ru.geekbrains.persist.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @SequenceGenerator(
            name = "products_sequence",
            sequenceName = "products_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "products_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Column(name = "product_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 65535)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    List<Picture> pictures = new ArrayList<>();
}
