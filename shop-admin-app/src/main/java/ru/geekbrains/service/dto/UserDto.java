package ru.geekbrains.service.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank
    private String login;
    @NotBlank
    private String password;

    private Set<RoleDto> roles;
}
