package ru.geekbrains.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.geekbrains.persist.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o " +
            "inner join fetch o.customer c " +
            "inner join fetch c.user u " +
            "where u.login = :login " +
            "order by o.id desc")
    List<Order> findAllByLogin(String login);

    @Query("select o from Order o " +
            "inner join fetch o.customer c " +
            "inner join fetch c.user u " +
            "inner join fetch o.orderLineItems oli " +
            "where o.id = :id " +
            "and u.login = :login")
    Optional<Order> findByIdAndLogin(long id, String login);
}
