package ru.geekbrains.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.geekbrains.persist.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o " +
            "inner join fetch o.customer c " +
            "inner join fetch c.user u " +
            "where u.login = :login")
    List<Order> findAllByLogin(String login);
}
