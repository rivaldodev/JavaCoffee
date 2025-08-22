package com.coffee.tracker.repository;

import com.coffee.tracker.model.User;
import com.coffee.tracker.dto.UserResponse;
import com.coffee.tracker.dto.UserRankingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("select new com.coffee.tracker.dto.UserResponse(u.id, u.name, u.email, coalesce(sum(cl.quantity),0)) from User u left join CoffeeLog cl on cl.user = u group by u")
    Page<UserResponse> findAllWithDrinkCounter(Pageable pageable);

    @Query("select new com.coffee.tracker.dto.UserResponse(u.id, u.name, u.email, coalesce(sum(cl.quantity),0)) from User u left join CoffeeLog cl on cl.user = u where u.id = :id group by u")
    Optional<UserResponse> findDtoById(@Param("id") Long id);

    @Query("select new com.coffee.tracker.dto.UserRankingResponse(u.id, u.name, u.email, sum(cl.quantity)) from CoffeeLog cl join cl.user u where cl.drinkTimestamp between :start and :end group by u order by sum(cl.quantity) desc")
    List<UserRankingResponse> findRankingBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
