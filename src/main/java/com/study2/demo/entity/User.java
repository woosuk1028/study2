package com.study2.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;
    private String nickname;
    private String role;

    private LocalDateTime lastLogin;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
