package ru.geekbrains.persist.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @SequenceGenerator(
            name = "brands_sequence",
            sequenceName = "brands_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "brands_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Column(name = "brand_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand")
    @ToString.Exclude
    private List<Product> products;
}
