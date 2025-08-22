package com.coffee.tracker.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    public User() {}
    public User(String name, String email, String password) { this.name = name; this.email = email; this.password = password; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    @Override public boolean equals(Object o){ if(this==o) return true; if(!(o instanceof User)) return false; return Objects.equals(id, ((User)o).id);} 
    @Override public int hashCode(){ return Objects.hash(id);} 
}
