package com.aybu9.aybualumni.user.models;

import javax.persistence.Entity;


public class UserDto {
    
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String surname;

    public UserDto(String id, String email, String password, String name, String surname) {
        this.id = Long.valueOf(id);
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
}
