package ru.geekbrains.service;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.model.User;
import ru.geekbrains.persist.repository.UserRepository;
import ru.geekbrains.persist.specification.UserSpecification;
import ru.geekbrains.service.dto.UserDto;
import ru.geekbrains.service.dto.UserMapper;

import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Override
    public Page<UserDto> findAll(Optional<String> loginFilter,
                                 Integer page,
                                 Integer size,
                                 String sortField,
                                 Sort.Direction direction) {
        Specification<User> spec = null;

        if (loginFilter.isPresent() && !loginFilter.get().isBlank()) {
            spec = Specification.where(
                    UserSpecification.nameLike(loginFilter.get().toLowerCase(Locale.ROOT)));
        }

        spec = combineSpec(spec, Specification.where(null));

        return userRepository.findAll(spec, PageRequest.of(page, size, Sort.by(direction, sortField)))
                .map(userMapper::fromUser);
    }

    @Override
    public Optional<UserDto> findById(long id) {
        return userRepository.findById(id).map(userMapper::fromUser);
    }

    @Transactional
    @Override
    public Optional<UserDto> findByLogin(String login) {
        return userRepository.findByLogin(login).map(userMapper::fromUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        user.setPassword(encoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.fromUser(user);
    }

    private <T> Specification<T> combineSpec(Specification<T> s1, Specification<T> s2) {
        return s1 == null ? Specification.where(s2) : s1.and(s2);
    }
}
