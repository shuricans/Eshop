package ru.geekbrains.service.dto;

import lombok.AllArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import ru.geekbrains.service.RoleService;

import java.util.Locale;

@AllArgsConstructor
@Component
public class RoleFormatter implements Formatter<RoleDto> {

    private final RoleService roleService;

    @Override
    public RoleDto parse(String name, Locale locale) {
        return roleService.findByName(name).orElse(null);
    }

    @Override
    public String print(RoleDto roleDto, Locale locale) {
        return roleDto.getName();
    }
}
