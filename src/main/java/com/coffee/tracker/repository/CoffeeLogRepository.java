package com.coffee.tracker.repository;

import com.coffee.tracker.model.CoffeeLog;
import com.coffee.tracker.model.User;
import com.coffee.tracker.dto.CoffeeLogResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface CoffeeLogRepository extends JpaRepository<CoffeeLog, Long> {
    @Query("select coalesce(sum(cl.quantity),0) from CoffeeLog cl where cl.user = :user")
    long totalQuantityByUser(@Param("user") User user);

    @Query("select new com.coffee.tracker.dto.CoffeeLogResponse(cl.id, cl.drinkTimestamp, cl.quantity) from CoffeeLog cl where cl.user = :user and cl.drinkTimestamp between :start and :end order by cl.drinkTimestamp asc")
    List<CoffeeLogResponse> findLogsForDay(@Param("user") User user, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("select new com.coffee.tracker.dto.CoffeeLogResponse(cl.id, cl.drinkTimestamp, cl.quantity) from CoffeeLog cl where cl.user = :user order by cl.drinkTimestamp desc")
    List<CoffeeLogResponse> findAllLogsDesc(@Param("user") User user);
}
