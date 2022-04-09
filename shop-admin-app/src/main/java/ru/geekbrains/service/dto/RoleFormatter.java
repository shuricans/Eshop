package ru.geekbrains.service.dto;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class RoleFormatter implements Formatter<RoleDto> {

    @Override
    public RoleDto parse(String name, Locale locale) {
        String[] arr = name.split(";");
        return new RoleDto(Long.parseLong(arr[0]), arr[1]);
    }

    @Override
    public String print(RoleDto roleDto, Locale locale) {
        return roleDto.getId() + ";" + roleDto.getName();
    }
}
