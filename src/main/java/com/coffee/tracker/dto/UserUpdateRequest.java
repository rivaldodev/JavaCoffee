package com.coffee.tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

// DTO para atualização parcial: todos os campos opcionais
public class UserUpdateRequest {
    private String name;
    @Email
    private String email; // validado se enviado
    @Size(min=6, max=100)
    private String password; // se enviado e não em branco será aplicado

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}