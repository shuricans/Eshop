package ru.geekbrains.service.dto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.geekbrains.persist.model.Customer;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CustomerMapper {

    Customer toCustomer(CustomerDto customerDto);

    @InheritInverseConfiguration
    CustomerDto fromCustomer(Customer customer);
}
