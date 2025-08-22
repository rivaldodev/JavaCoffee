package com.coffee.tracker.dto;

import java.time.LocalDateTime;

public class CoffeeLogResponse { private Long id; private LocalDateTime drinkTimestamp; private Integer quantity; public CoffeeLogResponse(Long id, LocalDateTime drinkTimestamp, Integer quantity){ this.id=id; this.drinkTimestamp=drinkTimestamp; this.quantity=quantity; } public Long getId(){ return id; } public LocalDateTime getDrinkTimestamp(){ return drinkTimestamp; } public Integer getQuantity(){ return quantity; } }
