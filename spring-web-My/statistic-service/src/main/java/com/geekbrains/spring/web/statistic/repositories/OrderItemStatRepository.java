package com.geekbrains.spring.web.statistic.repositories;

import com.geekbrains.spring.web.statistic.entities.OrderItemStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface OrderItemStatRepository extends JpaRepository<OrderItemStat, Long> {


    // TODO: на данный момент отбор проходит не корректно необходимо добавить GROUP BY o.id
    @Query(nativeQuery = true, value = "SELECT * FROM OrderItemStat o Where o.date > :OneMonthLate GROUP BY o.id Order by o.quantity DESC Limit 5")
    List<OrderItemStat> getTop5ForLastMonth(@Param("OneMonthLate")Date OneMonthLate);
}
