package com.example.unischedule.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "user_uni")
public class User {

    @Id
    public UUID id;
    //private String userName;
    public String email;
    public String password;

    public User() {
    }

    public User(UUID id, String password, String email) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
