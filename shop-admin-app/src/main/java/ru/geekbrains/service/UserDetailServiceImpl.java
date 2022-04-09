package ru.geekbrains.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByLogin(username)
                .map(user -> new User(
                        user.getLogin(),
                        user.getPassword(),
                        user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toList())
                )).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
