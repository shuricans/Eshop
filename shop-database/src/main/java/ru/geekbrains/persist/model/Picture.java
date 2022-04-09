package ru.geekbrains.persist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "picture")
public class Picture {

    @Id
    @SequenceGenerator(
            name = "pictures_sequence",
            sequenceName = "pictures_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "pictures_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Column(name = "picture_id")
    private Long id;

    @Column(name = "name", length = 1024, nullable = false)
    private String name;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "storage_file_name", length = 256, nullable = false, unique = true)
    private String storageFileName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
}
