package com.milosz000.dto;

import com.milosz000.model.Director;
import com.milosz000.model.enums.MovieGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private String name;
    private Director director;

    private MovieGenre genre;

    private String description;

    private Set<ActorDto> actors;
    private Integer release;
}
