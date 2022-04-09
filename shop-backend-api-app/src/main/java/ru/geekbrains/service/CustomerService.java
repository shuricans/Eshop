package ru.geekbrains.service;

import ru.geekbrains.service.dto.CustomerDto;

import java.util.Optional;

public interface CustomerService {

    Optional<CustomerDto> findByLogin(String login);
}
