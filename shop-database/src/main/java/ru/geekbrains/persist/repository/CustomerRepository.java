package ru.geekbrains.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.geekbrains.persist.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c " +
            "inner join fetch c.user u " +
            "inner join fetch u.roles " +
            "where u.login = :login")
    Optional<Customer> findByLogin(String login);
}
