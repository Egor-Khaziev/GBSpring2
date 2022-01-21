package com.geekbrains.spring.web.repositories;

import com.geekbrains.spring.web.entities.Order;
import com.geekbrains.spring.web.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByUser(User user);

}
