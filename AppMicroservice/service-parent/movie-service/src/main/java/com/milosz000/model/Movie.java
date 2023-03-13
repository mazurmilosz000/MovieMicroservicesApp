package com.milosz000.model;

import com.milosz000.model.enums.MovieGenre;
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
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @Enumerated(value = EnumType.STRING)
    private MovieGenre genre;

    private String description;


    @ManyToMany
    @JoinTable(name = "actorsMovies",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> stars;

    private Integer release;

}
