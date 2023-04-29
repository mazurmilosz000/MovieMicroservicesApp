package com.milosz000.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class GenericToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String token;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    LocalDateTime expiresAt;
}
