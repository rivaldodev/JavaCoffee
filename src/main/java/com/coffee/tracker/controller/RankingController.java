package com.coffee.tracker.controller;

import com.coffee.tracker.dto.UserRankingResponse;
import com.coffee.tracker.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    private final UserRepository userRepository;
    public RankingController(UserRepository userRepository){ this.userRepository = userRepository; }

    @GetMapping("/day")
    public List<UserRankingResponse> rankingDay(@RequestParam String date){
        LocalDate d = LocalDate.parse(date);
        LocalDateTime start = d.atStartOfDay();
        LocalDateTime end = d.atTime(LocalTime.MAX);
        return userRepository.findRankingBetween(start,end);
    }

    @GetMapping("/last")
    public List<UserRankingResponse> rankingLast(@RequestParam int days){
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusDays(days);
        return userRepository.findRankingBetween(start,end);
    }
}