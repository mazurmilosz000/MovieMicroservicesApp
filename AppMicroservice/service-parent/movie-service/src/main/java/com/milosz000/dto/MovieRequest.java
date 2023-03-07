package com.milosz000.dto;

import com.milosz000.model.enums.MovieGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {
    private String name;
    private String director;

    private MovieGenre genre;

    private String description;

    //TODO: create Actor entity and add relations
//    private Set<String> stars;
    private Integer release;
}
