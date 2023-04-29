package com.milosz000.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstname;
    String lastname;

    @ManyToMany(mappedBy = "stars")
    private Set<Movie> movies;
}
