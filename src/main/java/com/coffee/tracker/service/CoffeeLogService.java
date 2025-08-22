package com.coffee.tracker.service;

import com.coffee.tracker.dto.CoffeeLogResponse;
import com.coffee.tracker.model.CoffeeLog;
import com.coffee.tracker.model.User;
import com.coffee.tracker.repository.CoffeeLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate; import java.time.LocalDateTime; import java.time.LocalTime; import java.util.List;

@Service
public class CoffeeLogService { private final CoffeeLogRepository coffeeLogRepository; public CoffeeLogService(CoffeeLogRepository coffeeLogRepository){ this.coffeeLogRepository=coffeeLogRepository; }
    @Transactional public void add(User user, int quantity){ coffeeLogRepository.save(new CoffeeLog(user, LocalDateTime.now(), quantity)); }
    public long count(User user){ return coffeeLogRepository.totalQuantityByUser(user); }
    public List<CoffeeLogResponse> dayHistory(User user, LocalDate date){ LocalDateTime start=date.atStartOfDay(); LocalDateTime end=date.atTime(LocalTime.MAX); return coffeeLogRepository.findLogsForDay(user,start,end); }
    public List<CoffeeLogResponse> fullHistory(User user){ return coffeeLogRepository.findAllLogsDesc(user); }
}
