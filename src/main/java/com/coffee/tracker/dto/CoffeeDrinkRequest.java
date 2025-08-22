package com.coffee.tracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CoffeeDrinkRequest { @NotNull @Min(1) private Integer drink; public Integer getDrink(){ return drink; } public void setDrink(Integer drink){ this.drink=drink; } }
