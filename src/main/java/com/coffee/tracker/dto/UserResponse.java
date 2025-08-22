package com.coffee.tracker.dto;

public class UserResponse {
    private Long iduser; private String name; private String email; private long drinkCounter;
    public UserResponse(Long iduser, String name, String email, long drinkCounter){ this.iduser=iduser; this.name=name; this.email=email; this.drinkCounter=drinkCounter; }
    public Long getIduser(){ return iduser; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }
    public long getDrinkCounter(){ return drinkCounter; }
}
