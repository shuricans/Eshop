package ru.geekbrains.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.repository.CustomerRepository;
import ru.geekbrains.service.dto.CustomerDto;
import ru.geekbrains.service.dto.CustomerMapper;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDto> findByLogin(String login) {
        return customerRepository.findByLogin(login).map(customerMapper::fromCustomer);
    }
}
