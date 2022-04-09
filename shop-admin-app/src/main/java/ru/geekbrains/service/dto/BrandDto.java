package ru.geekbrains.service.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BrandDto {

    private Long id;

    @NotBlank
    private String name;
}
