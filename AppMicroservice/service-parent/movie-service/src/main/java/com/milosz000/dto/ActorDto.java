package com.milosz000.dto;

import com.milosz000.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActorDto {

    private String firstname;
    private String lastname;
    private Set<Movie> movies;
}
