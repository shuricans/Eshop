package ru.geekbrains.service.dto;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String login;

    private String password;

    private Set<RoleDto> roles;
}
