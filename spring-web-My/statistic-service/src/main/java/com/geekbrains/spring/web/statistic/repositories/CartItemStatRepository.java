package com.geekbrains.spring.web.statistic.repositories;

import com.geekbrains.spring.web.statistic.entities.CartItemStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemStatRepository extends JpaRepository<CartItemStat, Long> {
}
