package com.coffee.tracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "coffee_log")
public class CoffeeLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "drink_timestamp", nullable = false)
    private LocalDateTime drinkTimestamp;
    @Column(nullable = false)
    private Integer quantity;
    public CoffeeLog() {}
    public CoffeeLog(User user, LocalDateTime drinkTimestamp, Integer quantity){ this.user=user; this.drinkTimestamp=drinkTimestamp; this.quantity=quantity; }
    public Long getId(){ return id; }
    public User getUser(){ return user; }
    public void setUser(User user){ this.user=user; }
    public LocalDateTime getDrinkTimestamp(){ return drinkTimestamp; }
    public void setDrinkTimestamp(LocalDateTime drinkTimestamp){ this.drinkTimestamp=drinkTimestamp; }
    public Integer getQuantity(){ return quantity; }
    public void setQuantity(Integer quantity){ this.quantity=quantity; }
    @Override public boolean equals(Object o){ if(this==o) return true; if(!(o instanceof CoffeeLog)) return false; return Objects.equals(id, ((CoffeeLog)o).id);} 
    @Override public int hashCode(){ return Objects.hash(id);} 
}
