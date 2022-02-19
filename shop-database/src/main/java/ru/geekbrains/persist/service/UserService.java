package ru.geekbrains.persist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.geekbrains.persist.service.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Page<UserDto> findAll(Optional<String> loginFilter,
                          Integer page,
                          Integer size,
                          String sortField,
                          Sort.Direction direction);

    Optional<UserDto> findById(long id);

    Optional<UserDto> findByLogin(String login);

    void deleteById(Long id);

    UserDto save(UserDto user);
}
