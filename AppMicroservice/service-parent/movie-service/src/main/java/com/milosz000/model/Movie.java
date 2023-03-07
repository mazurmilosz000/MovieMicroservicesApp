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
    private String director;

    @Enumerated(value = EnumType.STRING)
    private MovieGenre genre;

    private String description;

    //TODO: create Actor entity and add relations
//    private Set<String> stars;
    private Integer release;

}
